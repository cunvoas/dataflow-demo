SET VERSION=0.0.1-SNAPSHOT
SET GCLOUD_PROJECT=dpsp-arbolpim
SET CLOUD_MACHINE=n1-standard-1
SET DATAFLOW_BUCKET=gs://dpsp-arbolpim_dataflow_2

SET with_extra=-DskipTests -Dexec.cleanupDaemonThreads=false
rem -s C:/Users/20010687/.m2/settings_jfrog.xml

SET PROJECT_TAGS=--projectId=%GCLOUD_PROJECT% --project=%GCLOUD_PROJECT%
SET REGION_TAGS=--region=europe-west4 --zone=europe-west4-b
SET WORKER=--workerMachineType=%CLOUD_MACHINE%
SET BUCKET_TAGS=--stagingLocation=%DATAFLOW_BUCKET%/staging/%VERSION% --gcpTempLocation=%DATAFLOW_BUCKET%/temp/%VERSION% --templateLocation=%DATAFLOW_BUCKET%/templates/%VERSION%/mydemo


rem mvn clean package %with_extra%
mvn %with_extra% exec:java -Dexec.mainClass=com.github.cunvoas.Bq2DsAll -Dexec.args="%PROJECT_TAGS% %REGION_TAGS% %WORKER%  %BUCKET_TAGS% --runner=DataflowRunner"

pause