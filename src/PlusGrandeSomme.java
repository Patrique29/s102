class PlusGrandeSomme{
    void principal(){
        int[] tab = {-1,8,-4,5,6,-9,-7,0,12};
        System.out.println("O : "+plusGrdeSomme1(tab, tab.length)[0]);
        System.out.println(plusGrdeSomme1(tab, tab.length)[1]);
        System.out.println(plusGrdeSomme1(tab, tab.length)[2]);
    }


    /**
     * TODO
     * @param tab the array 
     * @param n the length of the array
     * @return an array 
     */
    int[] plusGrdeSomme1(int[] tab,int n){
        //![max,ind1,ind2]
        //* add = max
        //* i = ind1
        //* i+ii-1 = ind2 or the index + its length - 1
        int add = 0;
        int[] result = new int[3];
        for(int i = 1; i < n; i++){
            // taille de la sous séquence
            for(int ii = 0; ii < n-i; ii++){

                for(int iii = 0;iii < ii ;iii++){
                    add += tab[i+iii];
                }
                if(add > result[0]){
                    result[0] = add;
                    result[1] = i;
                    result[2] = i + ii-1; // -1 a cause de la longueur
                }
                add = 0;
            }
        }


        return result;
    }









// METHODE DE TEST


    void testPlusGrdeSomme1(){

    }
}