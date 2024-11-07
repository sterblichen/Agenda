package com.chumbeke.agenda.ObjetosUsuariosYNotas;

public class Notas {
    private String nombre;
    private String correo;
    private String titulo;
    private String descripcion;
    private String fecha;

    public Notas(String nombre,String correo,String titulo,String descripcion,String fecha){
        this.nombre = nombre;
        this.correo = correo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
