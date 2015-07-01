cf cups config-service -p '{"uri":"http://config-server.cf.demo.local/"}'
cf cups service-registry -p '{"uri":"http://eureka.cf.demo.local/"}'
cf cs p-mysql 100mb-dev fortune-db
