# Prueba Tècnica

## Usuario de Prueba

Se genera un usuario ADMIN de manera automatica al iniciar la aplicacion. Las credenciales son:

* administrador@pruebatec.com
* administrador

## Login - POST /auth/login

Utilizar las credenciale mencionadas mas arriba. Retorna un token Jwt que debe utilizarse en 
el resto de endpoints.

### Cuerpo
    
    {
        "email":"administrador@pruebatec.com",
        "password":"administrador"
    }

### Retorna

    {
        "data": {
            "nombre": "Administrador",
            "apellido": "Administrador",
            "email": "administrador@pruebatec.com",
            "rol": "ADMIN",
            "token": "TOKEN_GENERADO"
        },
        "mensaje": "Sesión iniciada correctamente.",
        "status": 200
    }

## Listar Usuarios - GET /usuario/listar

Lista los usuarios registrados en la BD.

### Paràmetros - Filtros

* nombre : Nombre de usuario
* apellido : Apellido de usuario
* email : Email de usuario
* estado: Lista de estados de los usuarios a obtener, pueden ser activo o inactivo
* rol:  Lista de roles de los usuarios a obtener, pueden ser admin o consultor
* paginaNumero: Nùmero de la pagina a consultar, paginaciòn.
* size: Tamaño de la pàgina

### Ejemplo URL

        /usuario/listar?rol=admin,consultor&estado=inactivo,activo&nombre=administrador

### Retorna

    {
        "data": [
            {
                "id": 1,
                "nombre": "Administrador",
                "apellido": "Administrador",
                "email": "administrador@pruebatec.com",
                "rol": "ADMIN",
                "estado": "ACTIVO",
                "creado": "2024-10-26T15:01:16.684+00:00",
                "modificado": "2024-10-26T15:01:16.684+00:00"
            }
        ],
        "mensaje": "Datos Obtenidos correctamente",
        "status": 200
    }

## Registrar Usuario -  POST /usuario/crear

Registra un nuevo usuario en la BD, los emails deben ser ùnicos. 
Por default se registran como consultores.

### Autorizacion

    Bearer TOKEN_GENERADO

### Cuerpo

    {
        "nombre":"Fernando",
        "apellido":"Mendoza",
        "email":"fmendoza@test.com",
        "password":"123456"
    }

### Retorna

    {
        "data": {
            "id": 2,
            "nombre": "Fernando",
            "apellido": "Mendoza",
            "email": "fmendoza@test.com",
            "rol": "CONSULTOR",
            "estado": "ACTIVO",
            "creado": "2024-10-26T15:10:42.930+00:00",
            "modificado": "2024-10-26T15:10:42.930+00:00"
        },
        "mensaje": "Usuario creado correctamente",
        "status": 200
    }


## Modificar Usuario -  PUT /usuario/editar/{id}

    Permite editar un usuario.

### Campos a modificar

* nombre
* apellido
* email
* password
* rol : ADMIN o CONSULTOR 
* estado : ACTIVO O INACTIVO

### Cuerpo

    {
        "nombre": "Roberto",
        "estado":"INACTIVO"
    }

### Retorna
    {
        "data": {
            "id": 2,
            "nombre": "Roberto",
            "apellido": "Mendoza",
            "email": "fmendoza@test.com",
            "rol": "CONSULTOR",
            "estado": "INACTIVO",
            "creado": "2024-10-26T15:10:42.930+00:00",
            "modificado": "2024-10-26T15:21:22.636+00:00"
        },
        "mensaje": "Usuario editado correctamente",
        "status": 200
    }    

## Eliminar Usuario -  DELETE /usuario/eliminar/{id}

Permite desactivar un usuario

### Ejemplo URL

    /usuario/eliminar/2

### Retorna

    {
        "mensaje": "Usuario eliminado correctamente",
        "status": 200
    }