# Microservicios con spring boot, cloud security Docker y APIs

## Creando proyecto de maven en base a módulos usando IntelliJ IDEA

- Creamos un nuevo proyecto:
  ```
   Name: payment-chain-parent
   Location: lugar donde guardaremos el proyecto
   Language: Java
   Build System: Maven
   JDK: 17
   Advanced Settings
      GroupId: com.magadiflo.payment.chain
      ArtifactId: payment-chain-parent
  ```

- Al crear el proyecto, por defecto crea un directorio **/src**, pero como estamos creando un módulo, no lo necesitamos,
  así que lo eliminaremos para tener el proyecto lo más limpio que se pueda. NOTA: cuando guardamos con git, como los
  directorios del /src, están vacíos, no los toma en cuenta, así que posiblemente no estén en el commit inicial
  realizado.

## Creando primer módulo dentro de nuestro proyecto

- Click en el nombre del proyecto **payment-chain-parent/New/Module...**
- Completamos solo los siguientes campos con datos del nuevo módulo:
  ````
  Name: business-domain
  Parent: Por defecto estará seleccionada nuestro proyecto padre (raíz) payment-chain-parent
  ````
- Dejamos todos los demás campos por defecto y damos en Create.
- Al crear el nuevo módulo ocurren lo siguiente:
  ````
  - En el archivo pom.xml del padre, se cambia el tipo de packaging a pom, recordar que por defecto son jar.
  - Se registran en las siguientes etiquetas nuestro módulo creado.
  <modules>
    <module>business-domain</module>
  </modules>
  ````
- Este nuevo módulo también crea el directorio **/src**, pero como será un módulo (submódulo)
  eliminaremos dicho directorio.