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
package gui.componentes;

import gui.util.GestionPaneles;
import javax.swing.JPanel;

/**
 * Este componente representa a un JPanel de Swing que puede manejar la transición
 * de paneles, del panel superior. Esto consigue hacer un rastro que se puede seguir.
 * Por ejemplo, un rastro podría ser administración > clientes > nuevo. 
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.0
 */
public class JRoutedPanel extends JPanel {
    private GestionPaneles gesPanel;

    public JRoutedPanel(GestionPaneles gesPanel) {
        this.gesPanel = gesPanel;
    }

    public GestionPaneles getGestorPaneles() {
        return gesPanel;
    }
    
}
