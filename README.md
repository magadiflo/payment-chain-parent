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
  directorios del /src, están vacíos, no los toma en cuenta, así que posiblemente no estén en el commit inicial realizado.