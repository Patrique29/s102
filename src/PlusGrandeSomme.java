class PlusGrandeSomme{
    long cpt = 0;
    void principal(){
        int[] arr1 = {-1,8,-4,5,6,-9,-7,0,12};
        int[] arr2 = {-8,-4,6,8,-6,10,-4,-4};
        int[] arr3 = {-1,5,5,5,-80,3,3,3,3,3};
        int[] arr4 = {-1,3,3,3,3,3,-80,5,5,5};
        // int[] result = plusGrdeSomme3(arr1,arr1.length);
        // int[] result = plusGrdeSomme2(arr1,arr1.length);
        // int[] result = plusGrdeSomme4(arr4,arr2.length);
        // System.out.println(result[0]);
        // System.out.println(result[1]);
        // System.out.println(result[2]);
        testPlusGrdeSomme3Efficacite();
        int[] tableau = {-1,8,-4,5,6,-9,-7,0,12};

        int[] resultat = trouverSousSequenceMax(arr2, 0, arr2.length - 1);

        System.out.println("La somme maximale de la sous-séquence est : " + resultat[0]);
        System.out.println("Indices de la sous-séquence maximale : [" + resultat[1] + ", " + resultat[2] + "]");


        // testPlusGrdeSomme1();
        // testPlusGrdeSomme2();
        // testPlusGrdeSomme3();
        // testPlusGrdeSomme4();
        
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
     * @param arr
     * @param n
     * @return
     */
    int[] plusGrdeSomme2(int[] arr, int n){
        int[] result = new int[3];
        result[0] = arr[0];
        int somme;
        boolean onlyNegatives = true;
        for(int i = 0; i < n; i++){
            somme=0;
            if(arr[i] > 0) onlyNegatives = false;
            for(int j = i; j < n;j++){
                somme += arr[j];
                if(somme > result[0]){
                    result[0] = somme;
                    result[1] = i;
                    result[2] = j;
                }
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
            int max;

            //left side
            int maxL = arr[milieu];
            int[] resultLeft = calculer(arr,n,ind1,milieu,maxL);

            //right side
            int maxR = arr[milieu+1];
            int[] resultRight = calculer(arr,n,milieu+1,ind2,maxR);
            max = resultLeft[0] + resultRight[0];


            // System.out.println("a ind1 ="+ind1+"|ind2="+ind2+"| max="+max+"|result[0]="+result[0]);

            // System.out.println("n ind1 ="+ind1+"|ind2="+ind2+"| max="+max+"|result[0]="+result[0]);
            // System.out.println("---");
        
            int[] resultRecL = plusGrdeSommeRec3(arr, n,resultLeft[1],resultLeft[2],result);
            int[] resultRecR = plusGrdeSommeRec3(arr, n,resultRight[1],resultRight[2],result);
            //left
            if(resultRecL[0] > max){
                result[0] = resultRecL[0];
                result[1] = resultRecL[1];
                result[2] = resultRecL[2];
            }
            //right
            if(resultRecR[0] > max){
                result[0] = resultRecR[0];
                result[1] = resultRecR[1];
                result[2] = resultRecR[2];
            }

            
        }
        
        
        return result;
    }


    int[] trouverSousSequenceMax(int[] tableau, int debut, int fin) {
        if (debut == fin) {
            return new int[]{tableau[debut], debut, fin};
        }

        int milieu = (debut + fin) / 2;

        int[] gauche = trouverSousSequenceMax(tableau, debut, milieu);
        int[] droite = trouverSousSequenceMax(tableau, milieu + 1, fin);
        int[] croise = trouverSousSequenceCroiseMax(tableau, debut, milieu, fin);

        if (gauche[0] >= droite[0] && gauche[0] >= croise[0]) {
            return gauche;
        } else if (droite[0] >= gauche[0] && droite[0] >= croise[0]) {
            return droite;
        } else {
            return croise;
        }
    }

    int[] trouverSousSequenceCroiseMax(int[] tableau, int debut, int milieu, int fin) {
        int sommeGaucheMax = 0;
        int sommeCourante = 0;
        int debutGauche = 0;

        for (int i = milieu; i >= debut; i--) {
            sommeCourante += tableau[i];
            cpt++; //!do not count
            if (sommeCourante > sommeGaucheMax) {
                sommeGaucheMax = sommeCourante;
                debutGauche = i;
            }
        }

        int sommeDroiteMax = 0;
        sommeCourante = 0;
        int debutDroite = 0;

        for (int i = milieu + 1; i <= fin; i++) {
            sommeCourante += tableau[i];
            if (sommeCourante > sommeDroiteMax) {
                sommeDroiteMax = sommeCourante;
                debutDroite = i;
            }
        }

        int[] result = {sommeGaucheMax+sommeDroiteMax,debutGauche,debutDroite};

        return result;
    }


    /**
     * 
     * @param arr the array
     * @param n the size of the array
     * @param indD 
     * @param indF
     * @param max
     * @return
     */
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

    int[] plusGrdeSomme4(int[] arr,int n){
        int[] result = new int[3];
        int[] S = new int[n];
        int valMax;
        boolean onlyNegatives = true;
        int indDeb = 0;
        int indMax = 0;
        S[0] = arr[0];
        
        for(int i = 1; i<n; i++){
            if(arr[i-1] > 0 || arr[i] > 0) onlyNegatives = false;


            if(S[i-1] <= 0){
                S[i] = arr[i];
            }
            else{
                S[i] = arr[i] + S[i - 1];
            }
        }
        valMax = S[0];
        for(int j = 1; j<n; j++){
            if(valMax < S[j]){
                valMax = S[j];
                indMax = j;
            }
        }
        result[0] = valMax;
        result[2] = indMax;

        for(int m = indMax; m>0; m--){
            if(arr[m] == S[m]){
                indDeb = m;
                break;
            }
        }
        result[1] = indDeb;

        if(onlyNegatives){
            result[0] = 0;
            result[1] = 0;
            result[2] = 0;
        }

        
        return result;
    }

    // METHODE DE TEST
    
    
    void testPlusGrdeSomme1(){
        System.out.println();
        System.out.println("n^3");
        int[] arr1 = {-1,8,-4,5,6,-9,-7,0,12};
        int n1 = 9;
        int[] expect1 = {15,1,4};
        testCasPlusGrdeSomme1(arr1,n1,expect1);
        
        int[] arr2 = {-2,-5,-5,-10,-1,-50};
        int n2 = 6;
        int[] expect2 = {0,0,0};
        testCasPlusGrdeSomme1(arr2,n2,expect2);
        
        int[] arr3 = {-1,3,3,3,3,3,-80,5,5,5};
        int n3 = 6;
        int[] expect3 = {15,7,9};
        testCasPlusGrdeSomme1(arr3,n3,expect3);
    }
    void testPlusGrdeSomme2(){
            
        System.out.println();
        System.out.println("n^2");
        int[] arr1 = {-1,8,-4,5,6,-9,-7,0,12};
        int n1 = 9;
        int[] expect1 = {15,1,4};
        testCasPlusGrdeSomme2(arr1,n1,expect1);
        
        int[] arr2 = {-2,-5,-5,-10,-1,-50};
        int n2 = 6;
        int[] expect2 = {0,0,0};
        testCasPlusGrdeSomme2(arr2,n2,expect2);
        
        int[] arr3 = {-1,3,3,3,3,3,-80,5,5,5};
        int n3 = 6;
        int[] expect3 = {15,7,9};
        testCasPlusGrdeSomme2(arr3,n3,expect3);
    }

    void testPlusGrdeSomme3(){
        System.out.println();
        System.out.println("nlog2n");
        int[] arr1 = {-1,8,-4,5,6,-9,-7,0,12};
        int n1 = 9;
        int[] expect1 = {15,1,4};
        testCasPlusGrdeSomme3(arr1,n1,expect1);
        
        int[] arr2 = {-2,-5,-5,-10,-1,-50};
        int n2 = 6;
        int[] expect2 = {0,0,0};
        testCasPlusGrdeSomme3(arr2,n2,expect2);
        
        int[] arr3 = {-1,3,3,3,3,3,-80,5,5,5};
        int n3 = 6;
        int[] expect3 = {15,7,9};
        testCasPlusGrdeSomme3(arr3,n3,expect3);
    }


    void testPlusGrdeSomme4(){
        System.out.println();
        System.out.println("n");
        int[] arr1 = {-1,8,-4,5,6,-9,-7,0,12};
        int n1 = 9;
        int[] expect1 = {15,1,4};
        testCasPlusGrdeSomme4(arr1,n1,expect1);
        
        int[] arr2 = {-2,-5,-5,-10,-1,-50};
        int n2 = 6;
        int[] expect2 = {0,0,0};
        testCasPlusGrdeSomme4(arr2,n2,expect2);

        int[] arr3 = {-1,3,3,3,3,3,-80,5,5,5};
        int n3 = 6;
        int[] expect3 = {15,7,9};
        testCasPlusGrdeSomme4(arr3,n3,expect3);
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
    void testCasPlusGrdeSomme2(int[] arr,int n,int[] expect){
        int[] output = plusGrdeSomme2(arr,n);
        boolean exe = true;
        for(int i = 0;i < output.length; i++){
            if(output[i] != expect[i])
                exe = false;
        }
        if(exe) System.out.println("[OK]");
        else System.err.println("[ERROR]");
    }
    void testCasPlusGrdeSomme3(int[] arr,int n,int[] expect){
        int[] output = plusGrdeSomme3(arr,n);
        boolean exe = true;
        for(int i = 0;i < output.length; i++){
            if(output[i] != expect[i])
                exe = false;
        }
        if(exe) System.out.println("[OK]");
        else System.err.println("[ERROR]");
    }
    void testCasPlusGrdeSomme4(int[] arr,int n,int[] expect){
        int[] output = plusGrdeSomme4(arr,n);
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

    void testPlusGrdeSomme3Efficacite(){
        int maxN = (int) Math.pow(2, 6);
        int[] arr = new int[maxN];
        int min = -50, max = 50;
        long t1,t2,deltaT;
        double nlog2n;
        for(int i =0; i < maxN;i++){
            arr[i] = min + (int)(Math.random() * ((max - min) + 1));
        }

        for(int n = 2; n <= maxN; n*=2){
            cpt=0;
            t1 = System.nanoTime();
            trouverSousSequenceMax(arr, 0, n - 1);
            t2 = System.nanoTime();
            deltaT = t2-t1;
            System.out.println("Temps"+deltaT+"ns");
            System.out.println("cpt="+cpt);
            nlog2n = n*(Math.log10(n) / Math.log10(2));
            System.out.println("cpt/nlog2n = "+(double) (cpt/nlog2n));
            System.out.println("-----------");
        }

    }
}
