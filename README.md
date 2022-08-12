# DesafioWholemeaning

API Rest aún no finalizada debido a unos inconvenientes

Para el diseño de la API Rest se utilizó el patrón Repository, el cual trata de evitar el código
repetido, a través de una interface que implementa los métodos tradicionales de un CRUD, de esta
forma, si algún día se decide cambiar la base de datos Redis por otra SQL, simplemente se crea
una nueva clase que implemente esta interface.

La primera capa que tendrá conexión con el exterior, será la capa Controller. Esta capa, a su vez,
se comunicará con la capa de servicios, la cual accederá a las clases que implementen la interface
Repository.