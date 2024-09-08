package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            Factura factura1 = new Factura();

            factura1.setNumero(12);
            factura1.setFecha("01/09/2024");

            Domicilio dom = new Domicilio("San Martín", 423);
            Cliente cliente = new Cliente("Juan Manuel", "Puerta", 44057974);
            cliente.setDomicilio(dom);
            dom.setCliente(cliente);

            factura1.setCliente(cliente);

            Categoria perecederos = new Categoria("Perecederos");
            Categoria golosinas= new Categoria("Golosina");

            Articulo art1 = new Articulo(200, "Leche Ilolay", 21);
            Articulo art2 = new Articulo(100, "Alfajor Pepito", 3132);

            art1.getCategorias().add(perecederos);
            art2.getCategorias().add(golosinas);
            golosinas.getArticulos().add(art2);
            perecederos.getArticulos().add(art1);

            DetalleFactura det1 = new DetalleFactura();
            det1.setArticulo(art1);
            det1.setCantidad(2);
            det1.setSubtotal(40);

            art1.getDetalle().add(det1);
            factura1.getDetalles().add(det1);
            det1.setFactura(factura1);

            DetalleFactura det2 = new DetalleFactura();

            det2.setArticulo(art2);
            det2.setCantidad(4);
            det2.setSubtotal(8323);

            art1.getDetalle().add(det2);
            factura1.getDetalles().add(det2);
            det1.setFactura(factura1);

            factura1.setTotal(8363);
            entityManager.persist(factura1);



            entityManager.flush();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
        entityManagerFactory.close();
    }
}

