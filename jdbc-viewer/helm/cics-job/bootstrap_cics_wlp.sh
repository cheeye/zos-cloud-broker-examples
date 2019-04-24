#!/bin/bash
## This script is used to send the required files into a CICS + Liberty environment
echo "Creating and writing files to z/OSMF"
echo ""

# ENV variables provided directly from helm chart inputs
declare -r zosmf_user="$ZOSMF_USER"
declare -r zosmf_pass="$ZOSMF_PASS"

# ENV variables loaded by CICS secret
declare -r zosmf_host="$DFH_REGION_HOSTNAME"
declare -r cics_remote_base_dir="$DFH_REGION_ZFS_DIRECTORY/workdir/DFHWLP/wlp/usr/servers/defaultServer"
declare -r cics_base_file_url="https://$zosmf_user:$zosmf_pass@$zosmf_host/zosmf/restfiles/fs"

# ENV variables loaded by Db2 secret
declare -r db2_host="$DB2_HOSTNAME"
declare -r db2_port="$DB2_PORT"
declare -r db2_location="$DB2_LOCATION"
declare -r db2_schema="$DB2_SCHEMA"

# Creates an empty USS file (may error if one already exists, but that doesn't hurt anything)
create_file () {
  filepath=$1
  echo ""
  echo "Creating USS file: $filepath"

  file_url="$cics_base_file_url$filepath"

  echo "POST $file_url"
  echo ""

  curl -X POST $file_url \
        -k \
        -H 'X-CSRF-ZOSMF-HEADER: script' \
        -H 'Content-Type: application/json' \
        -d '{"type":"file","mode":"RWXRW-RW-"}'

  echo ""
  echo "Successfully created empty USS file: $filepath"
  echo ""
}

# Sends a local file over to a remote USS file
send_file () {
  source_filepath=$1
  filepath=$2
  data_type=$3

  file_url="$cics_base_file_url$filepath"

  echo ""
  echo "Reading source file: $source_filepath"
  echo "Destination USS file: $filepath"
  echo "PUT $file_url"
  echo ""

  if [ "$data_type" = "binary" ]; then
    echo "Writing binary data"

    curl -X PUT $file_url \
          -k \
          -H 'X-CSRF-ZOSMF-HEADER: script' \
          -H "X-IBM-Data-Type: $data_type" \
          --data-binary "@$source_filepath"
  else
    echo "Writing text data (performing ISO8559-1 translation)"
    data=$(<$source_filepath)

    curl -X PUT $file_url \
          -k \
          -H 'X-CSRF-ZOSMF-HEADER: script' \
          -H "X-IBM-Data-Type: $data_type" \
          -d "$data"
  fi

  echo ""
  echo "Successfully wrote to USS file: $filepath"
  echo ""
}

# Create an application specific `icp.xml`
create_app_xml () {
  echo ""
  echo "Creating icp.xml"
  echo "Setting Db2 Host: $db2_host"
  echo "Setting Db2 Port: $db2_port"
  echo "Setting Db2 Location: $db2_location"
  echo "Setting Db2 Schema: $db2_schema"

  cat > ./icp.xml <<EOF
<server>
  <library id="icpDb2Library">
    <fileset dir="/usr/lpp/db2/db2c10/jdbc/classes" includes="db2jcc4.jar db2jcc_license_cisuz.jar"/>
    <fileset dir="/usr/lpp/db2/db2c10/jdbc/lib" includes="*"/>
  </library>

  <dataSource id="jdbcViewerDataSource" jndiName="jdbc/jdbcViewerDb2">
		<jdbcDriver libraryRef="icpDb2Library"/>
		<properties.db2.jcc
      databaseName="$db2_location"
      driverType="4"
      password="$zosmf_pass"
      portNumber="$db2_port"
      serverName="$db2_host"
      user="$zosmf_user"
      timestampOutputType="1"
      queryCloseImplicit="1"
      allowNextOnExhaustedResultSet="1"
      timeFormat="1"
      timerLevelForQueryTimeOut="-1"
      currentSchema="$db2_schema"
    />
	</dataSource>
</server>
EOF

  echo ""
  echo "Successfully created application xml"
  echo ""
}

# Create icp.xml locally, used for later uploading to z/OS
create_app_xml

# Create USS file and write to it (icp.xml)
remote_icp_xml_filepath="$cics_remote_base_dir/icp.xml"
create_file $remote_icp_xml_filepath
send_file "./icp.xml" $remote_icp_xml_filepath binary

# Create USS file and write to it (JdbcViewerJaxRS.war)
remote_app_war_filepath="$cics_remote_base_dir/dropins/JdbcViewerJaxRS.war"
create_file $remote_app_war_filepath
send_file "./JdbcViewerJaxRS.war" $remote_app_war_filepath binary