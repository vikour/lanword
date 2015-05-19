package gui.util;

import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Stack;
import javax.swing.*;

/**
 * Gestiona los paneles que se van mostrando en la aplicación. Esta clase tiene una pila de profundidad de paneles. Para 
 * recordar los paneles anteriores relacionados con una determinada aplicación. Para este gestión paneles se necesita la
 * clase DefaultPanelComunidad que tiene los métodos onBack() y getTitle(). Esos métodos son usados por esta clase para 
 * obtener el nombre del panel y ejecutar el evento de atras respectivamente. El evento de atras es disparado cuando un
 * panel es ocultado.
 *
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.0
 * @date 17/11/2014
 * @see DefaultPanelComunidad
 */

public class GestionPaneles extends ComponentAdapter {
   private Stack<JPanel> stack;
   private Stack<Boolean> stackOption;
   private JPanel main;
   private boolean modoAsistente;
   private JLabel jLabelLocalizacion;
   
   /**
    * Este constructor necesita conocer el panel donde se van a mostrar los demás paneles y un JLabel para mostrar la 
    * localización de dónde está el usuario en ese momento.
    * 
    * @param main                Panel principal.
    * @param jLabelLocalizacion  JLabel donde saldrá la localización.
    */
   
   public GestionPaneles(JPanel main, JLabel jLabelLocalizacion) {
      this.main = main;
      stack = new Stack<>();
      stackOption = new Stack<>();
      modoAsistente = false;
      main.addComponentListener(this);
      this.jLabelLocalizacion = jLabelLocalizacion;
   }

   public void setModoAsistente(boolean modoAsistente) {
      this.modoAsistente = modoAsistente;
   }

   public boolean isModoAsistente() {
      return modoAsistente;
   }
   
   /**
    * Hace que un panel pasado como argumento se muestre en el panel principal. El panel que ya estaba (si es que había 
    * alguno) pasará a segundo plano. También hay que especificar si el panel estará centrado en el principal o expandido.
    * 
    * @param panel    Panel que se va a mostrar.
    * @param center   True si el panel estará centrado en el principal, false si estará extendido.
    */
   
   public void setVisiblePanel(JPanel panel, boolean center) {
      JPanel space = new JPanel();
      
      if (!stack.empty())
         main.remove((Component) stack.peek());
      
      stack.push(panel);
      stackOption.push(center);
      
      if (!center) 
         ((JPanel) panel).setSize(
            main.getWidth(),
            main.getHeight()
         );
      else {
         panel.setSize(
            panel.getPreferredSize().width,
            panel.getPreferredSize().height
         );
         panel.setBounds(
            (main.getWidth() - panel.getPreferredSize().width) / 2,
            (main.getHeight() - panel.getPreferredSize().height) / 2,
            panel.getPreferredSize().width,
            panel.getPreferredSize().height
         );
      }
      
      main.add((Component) panel);
      ((JPanel) panel).setVisible(true);
      main.updateUI();
   }
   
   /**
    * Vuelve un paso atrás en el panel principal mostrando el panel anterior al que se esta mostrando si éste existiera.
    * Este método ejecuta el evento onBack() del panel que se esta mostrando.
    */
   
   public void atras() {
      
      if (!stack.empty()) {
         // Se dispara el evento de atrás. Por si el panel tuviera que hacer algo antes de desaparecer.
         main.remove((Component) stack.peek());
         stack.pop();
         stackOption.pop();
         
         if (!stack.empty()) {
            
            if (!stackOption.peek()) // Si el panel no estaba centrado.
               ((JPanel) stack.peek())
                  .setSize(
                     main.getWidth(),
                     main.getHeight()
                  );
            else
               stack.peek().setBounds(
                  (main.getWidth() - stack.peek().getPreferredSize().width) / 2,
                  (main.getHeight() - stack.peek().getPreferredSize().height) / 2,
                  stack.peek().getPreferredSize().width,
                  stack.peek().getPreferredSize().height
               );
            
            main.add((Component) stack.peek());
         }
         
         stack.peek().setVisible(true);
         main.updateUI();
      }
      
   }
   
   
   
   public void reset() {
      
      if (!stack.empty()) {
         main.remove((Component) stack.peek());
      }
      
      main.updateUI();
      stack = new Stack<>();
   }

   @Override
   public void componentResized(ComponentEvent e) {

      super.componentResized(e); 
      
      if (!stack.empty()) {
         
         // Si está centrado
         if (stackOption.peek()) {
            stack.peek().setBounds(
               (main.getWidth() - stack.peek().getPreferredSize().width) / 2,
               (main.getHeight() - stack.peek().getPreferredSize().height) / 2,
               stack.peek().getPreferredSize().width,
               stack.peek().getPreferredSize().height
            );
         } 
         else
            stack.peek().setSize(main.getSize());

         main.updateUI();
         stack.peek().updateUI();
      }
   }
   
}
