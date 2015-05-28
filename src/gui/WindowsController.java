/*
 * Copyright 2015 Vikour.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gui;

import gui.administracion.JFrameMainAdministracion;
import gui.juegos.traducir_palabras.JDialogMain;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import lanword.interfaces.bd.BDResolver;
import lanword.interfaces.bd.IBDGestionPalabras;
import lanword.modelo.Idioma;
import lanword.modelo.Palabra;

public class WindowsController {
    
    private static WindowsController instance;

    private gui.AppStartMenu startMenu;
    private gui.administracion.JFrameMainAdministracion wAdministracion;
    private gui.juegos.traducir_palabras.JDialogMain wJuego;
    
    public static WindowsController getInstance() {
        
        if (instance == null)
            instance = new WindowsController();
            
        return instance;
    }

    public WindowsController() {
        startMenu = new AppStartMenu();
        wAdministracion = null;
        wJuego = null;
    }
    
    public void showMenu() {
        startMenu.setVisible(true);
    }
    
    public void showAdministracion() {
        
        if (wAdministracion == null)
            wAdministracion = new JFrameMainAdministracion();
        
        wAdministracion.setVisible(true);
    }
    
    public void showJuego() throws Exception {
        
        if (wJuego == null)
            wJuego = new JDialogMain();
        
        wJuego.setVisible(true);
    }
    
    public static boolean checkIfAnotherLanwordRuns() throws IOException {
        boolean otherLanwordRuns = false;
        File init = new File(".lanword");
        
        if (init.exists())
            otherLanwordRuns = true;
        else
            init.createNewFile();
        
        return otherLanwordRuns;
    };
    
    public static void lanwordEnded() {
        // Se borra el fichero .lanword.
        File f = new File(".lanword");
        f.delete();
    }
    
    private static void generarPalabras() throws SQLException, ClassNotFoundException, IOException {
        IBDGestionPalabras bd = BDResolver.getInstance().palabras;
        Idioma espanol = new Idioma("Español");
        Idioma ingles = new Idioma("Inglés");
        
        Palabra laptop = new Palabra("Laptop", ingles);
        Palabra pc = new Palabra("Personal Computer", ingles);
        Palabra hub = new Palabra("Hub", ingles);
        Palabra router = new Palabra("Router", ingles);
        Palabra monitor = new Palabra("Monitor", ingles);
        Palabra mouse = new Palabra("Mouse", ingles);
        Palabra keyboard = new Palabra("Keyboard", ingles);
        Palabra motherboard = new Palabra("Motherboard", ingles);
        Palabra harddisk = new Palabra("Hard disk", ingles);
        Palabra opticaldrive = new Palabra("Optical drive", ingles);
        Palabra printer = new Palabra("Printer", ingles);
        Palabra codification = new Palabra("Codificacion", ingles);
        Palabra software = new Palabra("Software", ingles);
        Palabra hardware = new Palabra("hardware", ingles);
        
        Palabra portatil = new Palabra("Portátil", espanol);
        Palabra pc_es = new Palabra("Ordenador personal", espanol);
        Palabra repetidor = new Palabra("Repetidor", espanol);
        Palabra encaminador = new Palabra("Encaminador", espanol);
        Palabra monitor_es = new Palabra("Monitor", espanol);
        Palabra raton = new Palabra("Ratón", espanol);
        Palabra teclado = new Palabra("Teclado", espanol);
        Palabra placabase = new Palabra("Placa base", espanol);
        Palabra discoduro = new Palabra("Disco duro", espanol);
        Palabra lectoroptico = new Palabra("Lector óptico", espanol);
        Palabra impresora = new Palabra("Impresora", espanol);
        Palabra codificacion = new Palabra("Codificación", espanol);
        Palabra software_es = new Palabra("Software", espanol);
        Palabra hardware_es = new Palabra("Hardware", espanol);
        
        laptop.anyadirTraduccion(portatil);
        pc.anyadirTraduccion(pc_es);
        hub.anyadirTraduccion(repetidor);
        router.anyadirTraduccion(encaminador);
        monitor.anyadirTraduccion(monitor_es);
        mouse.anyadirTraduccion(raton);
        keyboard.anyadirTraduccion(teclado);
        motherboard.anyadirTraduccion(placabase);
        harddisk.anyadirTraduccion(discoduro);
        opticaldrive.anyadirTraduccion(lectoroptico);
        printer.anyadirTraduccion(impresora);
        codification.anyadirTraduccion(codificacion);
        software.anyadirTraduccion(software_es);
        hardware.anyadirTraduccion(hardware_es);
        
        bd.anyadir(laptop);
        bd.anyadir(pc);
        bd.anyadir(hub);
        bd.anyadir(router);
        bd.anyadir(monitor);
        bd.anyadir(mouse);
        bd.anyadir(keyboard);
        bd.anyadir(motherboard);
        bd.anyadir(harddisk);
        bd.anyadir(opticaldrive);
        bd.anyadir(printer);
        bd.anyadir(codification);
        bd.anyadir(software);
        bd.anyadir(hardware);
    };
}
