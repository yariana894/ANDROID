package com.example.fragmentcontactos;


//Clase encargada de almacenar los valores del nombre, apellido, telefono y direccion de un contacto.
public class Contacto {

    private String nombre;
    private String apellidos;
    private String telefono;
    private String direccion;

    public Contacto(String nombre, String apellidos, String telefono, String direccion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    //Devuelve el valor almacenado en el campo nombre
    public String getNombre() {
        return nombre;
    }

    //Almacena un valor en el campo nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getApellidos() {
        return apellidos;
    }


    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }


    public String getTelefono() {
        return telefono;
    }


    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public String getDireccion() {
        return direccion;
    }


    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


}
