class PlusGrandeSomme{
    long cpt = 0;
    void principal(){
        int[] arr1 = {-1,8,-4,5,6,-9,-7,0,12};
        int n1 = 9;
        System.out.println(plusGrdeSomme3(arr1,n1)[0]);
        System.out.println(plusGrdeSomme3(arr1,n1)[1]);
        System.out.println(plusGrdeSomme3(arr1,n1)[2]);
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
        int[] result = new int[3];
        int add = 0;
        boolean onlyNegatives = true;
        for(int index = 1; index < n; index++){
            if(arr[index] > 0) onlyNegatives = false;
            // taille de la sous séquence
            for(int lengthSubsequence = 0; lengthSubsequence < n-index; lengthSubsequence++){
                for(int iii = 0;iii < lengthSubsequence ;iii++){
                    cpt++; //!do not count
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
    
    
    /**
     * TODO
     * @param arr the array
     * @param n length of the array
     * @return an array
     */
    int[] plusGrdeSomme3(int[] arr, int n){
        //![max,ind1,ind2]
        int ind1 = 0,ind2 =n-1;
        int[] result = new int[3];
        
        result = plusGrdeSommeRec3(arr,n,ind1,ind2,result);

        return result;
    }

    int[] plusGrdeSommeRec3(int[] arr, int n,int ind1,int ind2,int[] pastResult){
        int[] result = pastResult;
        
        if (ind1 == ind2)
        {
            result[0] = arr[ind1];
            result[1] = ind1;
            result[2] = ind2;
        }else{
            int milieu = (ind1 + ind2)/2;
            int[] resultLeft;
            int[] resultRight;
            int max;

            //left side
            int maxL = arr[milieu];
            resultLeft = calculer(arr,n,ind1,milieu,maxL);

            //right side
            int maxR = arr[milieu+1];
            resultRight = calculer(arr,n,milieu+1,ind2,maxR);
            max = resultLeft[0] + resultRight[0];
            System.out.print("ancien ind1 ="+ind1+"|ind2="+ind2);
            System.out.println("| max="+max+"|result[0]="+result[0]);
            if(max > result[0]){
                result[0] = max;
                result[1] = ind1;
                result[2] = ind2;
            }
            System.out.print("ind1 ="+ind1+"|ind2="+ind2);
            System.out.println("|max="+max);
            if(ind1 != milieu)
                plusGrdeSommeRec3(arr, n,ind1,milieu,result);
            if(milieu+1 != ind2)
                plusGrdeSommeRec3(arr, n,milieu+1,ind2,result);

            
        }
        
        
        return result;
    }

    int[] calculer(int[] arr, int n,int indD,int indF,int max){
        int[] result = new int[3];
        int somme = 0;
        for(int i =indD; i <= indF; i++){
            somme += arr[i];
            if(somme > max){
                result[0] = somme;
                result[1] = i;
                result[2] = indF;
            }
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
    void testPlusGrdeSomme1Efficacite(){


    }

}
