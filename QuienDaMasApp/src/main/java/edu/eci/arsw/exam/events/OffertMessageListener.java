package edu.eci.arsw.exam.events;

import java.util.Random;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import edu.eci.arsw.exam.IdentityGenerator;
import edu.eci.arsw.exam.Product;
import edu.eci.arsw.exam.remote.ManejadorOfertasStub;

public class OffertMessageListener implements MessageListener {

    Random rand = new Random(System.currentTimeMillis());

    private ManejadorOfertasStub manejadorOfertasStub;

    public void setManejadorOfertasStub(ManejadorOfertasStub manejadorOfertasStub) {
        this.manejadorOfertasStub = manejadorOfertasStub;
    }

    public OffertMessageListener() {
        super();
        System.out.println("Comprador #"+IdentityGenerator.actualIdentity+" esperando eventos...");
    }

    @Override
    public void onMessage(Message message) {
        try {
            Product receivedProduct = new Product(message.getBody());
            System.out.println("Comprador #"+IdentityGenerator.actualIdentity+" recibió: "+receivedProduct.getCode());
            
            int montoOferta = Math.abs(rand.nextInt(99999999));

            //realizar oferta con el monto aleatorio generado
            if (manejadorOfertasStub != null) {
                manejadorOfertasStub.agregarOferta(IdentityGenerator.actualIdentity, receivedProduct.getCode(), montoOferta);
                System.out.println("Comprador #"+IdentityGenerator.actualIdentity+" realizó una oferta de: "+montoOferta);
            } else {
                System.out.println("Comprador #"+IdentityGenerator.actualIdentity+" no pudo ofertar (stub no configurado)");
            }
            
        } catch (Exception e) {
            throw new RuntimeException("An exception occured while trying to get a AMQP object:" + e.getMessage(), e);
        }

    }

    

}
