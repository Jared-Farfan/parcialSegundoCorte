/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.exam.remote;

import edu.eci.arsw.exam.FachadaPersistenciaOfertas;

/**
 *
 * @author hcadavid
 */
public class ManejadorOfertasSkeleton implements ManejadorOfertasStub{

    private FachadaPersistenciaOfertas fpers=null;

    private edu.eci.arsw.exam.MainFrame mainFrame=null;

    public void setFachadaPersistenciaOfertas(FachadaPersistenciaOfertas fpers) {
        this.fpers = fpers;
    }

    public void setMainFrame(edu.eci.arsw.exam.MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
            
    @Override
    public void agregarOferta(String codOferente,String codprod,int monto) {
        
        if (!fpers.getMapaOfertasRecibidas().containsKey(codprod)){
            //se ha recibido la primera oferta 
            fpers.getMapaOfertasRecibidas().put(codprod, 1);
            //se asigna el monto propuesto como mejor oferta
            fpers.getMapaMontosAsignados().put(codprod, monto);
            //se asigna al oferente como ganador provisional
            fpers.getMapaOferentesAsignados().put(codprod, codOferente);
        }
        else{
            int ofertasActuales=fpers.getMapaOfertasRecibidas().get(codprod);
            int ofertasNuevas=ofertasActuales+1;
            fpers.getMapaOfertasRecibidas().put(codprod,ofertasNuevas);
            if (fpers.getMapaMontosAsignados().get(codprod)>monto){
                fpers.getMapaMontosAsignados().put(codprod, monto);
                fpers.getMapaOferentesAsignados().put(codprod, codOferente);
            }
        }

        Integer totalOfertas = fpers.getMapaOfertasRecibidas().get(codprod);
        if (totalOfertas != null && totalOfertas == 3 && mainFrame != null) {
            String ganador = fpers.getMapaOferentesAsignados().get(codprod);
            Integer montoGanador = fpers.getMapaMontosAsignados().get(codprod);
            if (ganador != null && montoGanador != null) {
                mainFrame.appendAcceptedOffer(codprod, ganador, montoGanador);
            }
        }
        
    }
    
    
    
}
