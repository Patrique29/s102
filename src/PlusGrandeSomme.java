class PlusGrandeSomme{
    long cpt = 0;
    void principal(){
        testPlusGrdeSomme1();
    }


    /**
     * TODO
     * @param arr the array 
     * @param n the length of the array
     * @return an array 
     */
    int[] plusGrdeSomme1(int[] arr,int n){
        //![max,ind1,ind2]
        //* add = max
        //* index = ind1
        //* index+lengthSubsequence-1 = ind2 or the index + its length - 1
        int add = 0;
        boolean onlyNegatives = true;
        int[] result = new int[3];
        for(int index = 1; index < n; index++){
            if(arr[index] > 0) onlyNegatives = false;
            // taille de la sous séquence
            for(int lengthSubsequence = 0; lengthSubsequence < n-index; lengthSubsequence++){
                for(int iii = 0;iii < lengthSubsequence ;iii++){
                    add += arr[index+iii];
                }
                if(add > result[0]){
                    result[0] = add;
                    result[1] = index;
                    result[2] = index + lengthSubsequence-1; // -1 because of the length
                }
                add = 0;
            }
        }

        if(onlyNegatives){
            result[0] = 0;
            result[1] = 0;
            result[2] = 0;
        }

        return result;
    }


// METHODE DE TEST


    void testPlusGrdeSomme1(){
        int[] arr1 = {-1,8,-4,5,6,-9,-7,0,12};
        int n1 = 9;
        int[] expect1 = {15,1,4};
        testCasPlusGrdeSomme1(arr1,n1,expect1);
        
        int[] arr2 = {-2,-5,-5,-10,-1,-50};
        int n2 = 6;
        int[] expect2 = {0,0,0};
        testCasPlusGrdeSomme1(arr2,n2,expect2);

    }


//METHODE TEST CAS

    void testCasPlusGrdeSomme1(int[] arr,int n,int[] expect){
        int[] output = plusGrdeSomme1(arr,n);
        boolean exe = true;
        for(int i = 0;i < output.length; i++){
            if(output[i] != expect[i])
                exe = false;
        }
        if(exe) System.out.println("[OK]");
        else System.err.println("[ERROR]");
    }

//METHODE EFFICACITE


}
