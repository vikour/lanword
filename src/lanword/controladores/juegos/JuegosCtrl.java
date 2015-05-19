/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanword.controladores.juegos;

/**
 * Permite a varias vistas acceder a la partida en curso o no.
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.0
 * @date 03/04/2015
 */
public class JuegosCtrl {
    private PartidaJuegoTraducir partida;
    private static JuegosCtrl instance;

    public static JuegosCtrl getInstance() {
        
        if (instance == null)
            instance = new JuegosCtrl();
        
        return instance;
    }
    
    private JuegosCtrl() {}

    public void setPartida(PartidaJuegoTraducir partida) {
        this.partida = partida;
    }

    public PartidaJuegoTraducir getPartida() {
        return partida;
    }
    
}
