/*
 * Copyright 2015 Blackleg blackleg@openaliasbox.org.
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
package es.blackleg.java.objects;

import es.blackleg.java.exceptions.DniFormatException;
import es.blackleg.java.exceptions.DniIncorrectException;
import es.blackleg.java.exceptions.StringEmptyException;
import es.blackleg.java.utilities.Validator;
import java.util.Objects;

/**
 *
 * @author Blackleg blackleg@openaliasbox.org
 */
public class Dni {
    
    private String letra;
    private String numeros;
    
    public Dni(String dni) throws DniFormatException, DniIncorrectException, StringEmptyException {
        dni = checkDni(dni);
        letra = getLetraDni(dni);
        numeros = getNumerosDni(dni);
    }

    public void setDni(String dni) throws DniFormatException, DniIncorrectException, StringEmptyException {
        dni = checkDni(dni);
        letra = getLetraDni(dni);
        numeros = getNumerosDni(dni);

    }

    public String toStringCompact() {
        return numeros + letra;
    }
    
    @Override
    public String toString() {
        return String.format("DNI: %s%s", numeros, letra);
    }
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.letra);
        hash = 83 * hash + Objects.hashCode(this.numeros);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Dni other = (Dni) obj;
        if (!Objects.equals(this.letra, other.letra)) {
            return false;
        }
        return Objects.equals(this.numeros, other.numeros);
    }
    
    /**
     * Calculate dni letter
     * Calcula letra dni
     * @param dniNumber The dni number
     * @return dni letter
     */
    public static String calculateLetter(int dniNumber) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKEU";
        int indice = dniNumber % 23;
        return letras.substring(indice, indice + 1);
    }
    
    private static String checkDni(String dni) throws DniFormatException, DniIncorrectException, StringEmptyException {
        dni = checkString(dni);

        String numeros = getNumerosDni(dni);
        String letra = getLetraDni(dni);

        if (checkDni(numeros, letra)) {
            return dni;
        } else {
            throw new DniIncorrectException();
        }

    }

    private static String getLetraDni(String dni) {
        return dni.substring(dni.length() - 1, dni.length());
    }

    private static String getNumerosDni(String dni) {
        return dni.substring(0, 8);
    }


    private static boolean checkDni(String numeros, String letra ) {
        return calculateLetter(Integer.valueOf(numeros)).equals(letra);
    }


    private static String checkString(String string) throws DniFormatException, StringEmptyException {
        if (Validator.checkStringIsDni(string)) {
            return string;
        } else {
            throw new DniFormatException();
        }
    }
    
}
