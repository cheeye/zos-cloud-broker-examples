FROM ubuntu

RUN apt-get update
RUN apt-get install -y curl

RUN mkdir -p /job

COPY JdbcViewerJaxRS.war /job
COPY bootstrap_cics_wlp.sh /job

# Copy over the application file
COPY JdbcViewerJaxRS.war /config/dropins/

WORKDIR /job
