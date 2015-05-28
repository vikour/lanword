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

package lanword.controladores.juegos;

import java.util.ArrayList;
import lanword.modelo.Idioma;
import lanword.modelo.Palabra;

/**
 * Representa una partida en curso o finalizada por el usuario. De esta se quiere 
 * conocer las palabras que han participado en la partida y su resultado.
 * 
 * @author Víctor Manuel Ortiz Guardeño
 * @version 1.0
 * @date 4/04/2015
 */

public class PartidaJuegoTraducir {
    private ArrayList<Palabra> palabras;
    private ArrayList<Palabra> aciertos;
    private ArrayList<Palabra> fallos;
    private ArrayList<String> respuestas_fallos;
    private int contador;
    private Idioma idiomaObjectivo;
    
    /**
     * Construye una partida con unas palabras de inicio.
     * 
     * @param palabras Palabras de la partida.
     */
    
    public PartidaJuegoTraducir(ArrayList<Palabra> palabras, Idioma objetivo) {
        this.palabras = palabras;
        aciertos = new ArrayList<>();
        fallos = new ArrayList<>();
        respuestas_fallos = new ArrayList<>();
        contador = 0;
        idiomaObjectivo = objetivo;
    }
    
    /**
     * Permite obtener la palabra que se va a evaluar. Si a través de este método se 
     * obtiene un NULL, significa fin de la partida.
     * 
     * @return Palabra que se evaluará o NULL para fin de la partida.
     */
    
    public Palabra getPalabra() {
        
        if (contador < palabras.size()) {
            return palabras.get(contador);
        }
        else 
            return null;
    }
    
    /**
     * Marca la palabra que se va a evaluar (obtenida por getPalabra()) como acierto por
     * el usuario, y pasa a la siguiente palabra para evaluar. Esta se consigue a través
     * del método getPalabra()
     */
    
    private void anyadirAcierto() {
        
        if (!aciertos.contains(palabras.get(contador))) {
            aciertos.add(palabras.get(contador));
            contador++;
        }
            
    }
    
    /**
     * Marca la palabra que se va a evaluar como fallo, y pasa a la siguiente palabra para
     * evaluar. Esta se consigue a través del método getPalabra().
     */
    
    private void anyadirFallo() {
        
        if (!fallos.contains(palabras.get(contador))) {
            fallos.add(palabras.get(contador));
            contador++;
        }
    }
    
    /**
     * Permite añadir una respuesta a la palabra evaluada. Esta puede ser acertada o fallida,
     * cuando se use este método, el estado de la partida pasará a evaluar la siguiente palarba.
     * Esta se consigue mediante el método getPalabra().
     * 
     * @param palabra Traducción.
     */
    
    public void anyadirRespuesta(String palabra) {
        String pre_palabra;
        String pre_n_palabra;
        boolean acierto = false;
        
        // Quiere decir, que no ha terminado la partida.
        if (contador != palabras.size()) {
            pre_palabra = prepararRespuesta(palabra);
            
            for (Palabra traduccion : palabras.get(contador).getTraducciones(idiomaObjectivo)) {
                pre_n_palabra = prepararRespuesta(traduccion.getNombre());
                if (pre_n_palabra.matches(pre_palabra)) {
                    anyadirAcierto();
                    acierto = true;
                }

            }
            
            if (!acierto) { 
                anyadirFallo();
                respuestas_fallos.add(palabra);
            }
            
        }
        
    }
    
    /**
     * Permite conocer qué respondió el usuario en un fallo.
     * 
     * @param palabra Palabra en la que ha habido un fallo.
     * @return Traducción fallida del usuario.
     */
    
    public String getRespuestaAlFallo(Palabra palabra) {
        
        for (int i = 0 ; i < fallos.size() ; i++)
            
            if (palabra.equals(fallos.get(i)))
                return respuestas_fallos.get(i);

        return null;
    }
    
    /**
     * Permite obtener la puntuación sobre 10. La partida tiene que estar terminada, si no,
     * devolverá -1.
     * 
     * @return -1 si no ha terminado la partida y la puntuación si ha terminado.
     */
    
    public float getPuntuacion() {

        if (contador == palabras.size())
            return (float) (aciertos.size() * 100) / (palabras.size() * 10);
        else
            return -1;
    }

    public ArrayList<Palabra> getFallos() {
        return fallos;
    }

    public ArrayList<Palabra> getAciertos() {
        return aciertos;
    }

    public Idioma getIdiomaObjectivo() {
        return idiomaObjectivo;
    }
    
    public static String prepararRespuesta(String palabra) {
        String out = palabra.toLowerCase().replace("á", "a").replace("é", "e")
                      .replace("í", "i").replace("ó", "o").replace("ú", "u")
                      .trim();
        
        return out;
    }
}
