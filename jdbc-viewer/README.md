# JDBC Viewer
This application shows a declarative example of provisioning a microservice architecture using ICP, Kubernetes, and z/OS.

## Backend on z/OS, Frontend on ICP
This Deployment Option will provision a Db2 Schema and CICS + WLP on z/OS. Once they are provisioned, the Bindings (and Secrets) that these
service instances create are used in a Kubernetes `Job` to Bootstrap the z/OS CICS Environment. Finally, the Frontend Kubernetes Pod connects
to the Backend application on CICS using the Secrets created by the CICS Binding.

During Bootstrapping, the following steps are taken:

1. Read CICS z/OS USS directory from the `DFH_REGION_ZFS_DIRECTORY` Secrets value
1. Read the Db2 Schema Secret values to construct a JNDI binding
1. Create JNDI binding `server.xml` extension using Db2 Secrets
1. Upload Backend Application WAR file to CICS USS directory
1. Upload Backend Application `server.xml` extension

This helm chart will provision the following Kubernetes resources:

| **Resource**                    | **Type**             |
|---------------------------------|----------------------|
| z/OS Db2 Schema Service         | ServiceInstance      |
| z/OS Db2 Schema Service Binding | ServiceBinding       |
| z/OS Db2 Schema Binding Secret  | Secret               |
| z/OS CICS + WLP Service         | ServiceInstance      |
| z/OS CICS + WLP Service Binding | ServiceBinding       |
| z/OS CICS + WLP Binding Secret  | Secret               |
| CICS + WLP Backend Bootstrapper | Job                  |
| Node.JS Vue.JS Frontend         | Deployment & Service |

### Architecture
These resources are tied together using the following architecture:
![](https://i.imgur.com/l7JJVrb.png)

## Db2 Schema on z/OS, Backend and Frontend on ICP
This Deployment Option will provision a Db2 Schema on z/OS. Once the schema is provisioned, the Binding (and Secrets) that the Db2 Schema
service instance creates are used in the Backend Application Deployment. Finally, the Frontend Kubernetes Pod connects
to the Backend application using Kubernetes routing.

This helm chart will provision the following Kubernetes resources:

| **Resource**                    | **Type**             |
|---------------------------------|----------------------|
| z/OS Db2 Schema Service         | ServiceInstance      |
| z/OS Db2 Schema Service Binding | ServiceBinding       |
| z/OS Db2 Schema Binding Secret  | Secret               |
| WebSphere Liberty Java Backend  | Deployment & Service |
| Node.JS Vue.JS Frontend         | Deployment & Service |

## Architecture
These resources are tied together using the following architecture:
![](https://i.imgur.com/JE87LRx.png)
