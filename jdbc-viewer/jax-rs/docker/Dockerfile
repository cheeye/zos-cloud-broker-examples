FROM websphere-liberty:kernel

# Copy over base server configuration
COPY server.xml /config/
RUN installUtility install --acceptLicense defaultServer

# Copy over the JDBC jars
RUN mkdir -p /config/lib
COPY lib/*.jar /config/lib/

# Copy over the application file
COPY JdbcViewerJaxRS.war /config/dropins/

EXPOSE 9080
EXPOSE 9443