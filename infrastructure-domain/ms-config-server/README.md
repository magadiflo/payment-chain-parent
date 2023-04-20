# Centralizar la configuración: Comprobar acceso al resositorio desde el server conf
- Para poder ver las propiedades configuradas en cada archivo de configuración, debemos
usar el puerto definido en el archivo de configuración del config-server cuya ruta 
será igual al nombre definido en los archivos de configuraciones.
- A nuestro config-server le asignamos el puerto 8888.
- Supongamos que tenemos los siguientes archivos de configuraciones en nuestro repositorio local:
````
config-client-development.properties
config-client-production.properties

ms-productos.properties
ms-productos-dev.properties
ms-productos-staging.properties
ms-productos-prod.properties
````
- Para poder acceder por la web al contenido de cada archivo de propiedades, haríamos así:
> http://localhost:8888/prefijo_del_nombre_del_archivo/ambiente
- Usando los ejemplos de los microservicios:

````
http://localhost:8888/config-client/development
http://localhost:8888/config-client/production

http://localhost:8888/ms-productos/default
http://localhost:8888/ms-productos/dev
http://localhost:8888/ms-productos/staging
http://localhost:8888/ms-productos/prod
````    