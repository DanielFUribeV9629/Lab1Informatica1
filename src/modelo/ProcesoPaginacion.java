
package modelo;


public class ProcesoPaginacion {
        private String name;
        private int size;
        private int byteAddress1;
        private int byteAddress2;
        private String strAddress1;
        private String strAddress2;
        
        //METODO CONSTRUCTOR
        public ProcesoPaginacion(String newName, int newSize)
        {
            name = newName;
            size = newSize;
            strAddress1="";
            strAddress2="";
        }
        //METODOS SET
        public void setByteAddress1(int newByteAddress1)
        {
            byteAddress1 = newByteAddress1;
            strAddress1 = ConversionHex(byteAddress1);
        }
        public void setByteAddress2(int newByteAddress2)
        {
            byteAddress2 = newByteAddress2;
            strAddress2 = ConversionHex(byteAddress2);
        }
        
        //METODOS GET
        public int getSize()
        {
            return size;
        }
        public String getName()
        {
            return name;
        }
        public int getByteAddress1()
        {
            return byteAddress1;
        }
        public int getByteAddress2()
        {
            return byteAddress2;
        }
        public String getStrAddress1()
        {
            return strAddress1;
        }
        public String getStrAddress2()
        {
            return strAddress2;
        }
        
        public String ConversionHex (int number){
            String hexadecimal = "";
            String caracteresHexadecimales = "0123456789abcdef";
            while (number > 0) {
                int residuo = number % 16;
                hexadecimal = caracteresHexadecimales.charAt(residuo) + hexadecimal;
                number /= 16;
            }
            return "0x"+hexadecimal.toUpperCase();
        }
        
        @Override
        public String toString() {
            return name + " (" +String.valueOf(size) + ") bits "+strAddress1+" "+strAddress2; 
        }
    
}
