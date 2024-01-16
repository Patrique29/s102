/**
 * biggest sum in a subsequence
 * @author Rayanne M., Maël C.
 */
class PlusGrandeSomme{
    long cpt = 0;
    /**
     * principal
     */
    void principal(){
        testPlusGrdeSomme1();
        testPlusGrdeSomme2();
        testPlusGrdeSomme3();
        testPlusGrdeSomme4();
        testPlusGrdeSomme1Efficacite();
    }


    /**
     * efficiency in Θ(n^3)
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
     * efficiency in Θ(n^2)
     * @param arr
     * @param n
     * @return
     */
    int[] plusGrdeSomme2(int[] arr, int n){
        int[] result = new int[3];
        result[0] = arr[0];
        int sum;
        boolean onlyNegatives = true;
        for(int i = 0; i < n; i++){
            sum=0;
            if(arr[i] > 0) onlyNegatives = false;
            for(int j = i; j < n;j++){
                sum += arr[j];
                if(sum > result[0]){
                    result[0] = sum;
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
     * efficiency in Θ(nlog2(n))
     * @param arr the array
     * @param n length of the array
     * @return an array
     */
    int[] plusGrdeSomme3(int[] arr, int n){
        //![max,ind1,ind2]
        int ind1 = 0,ind2 =n-1;
        int[] result = new int[3];
        
        result = plusGrdeSommeRec3(arr,n,ind1,ind2);

        return result;
    }

    
    int[] plusGrdeSommeRec3(int[] arr,int n, int start, int end) {
        int[] result = new int[3];
        if (start == end) {
            result[0] = arr[start];
            result[1] = start;
            result[2] = end;
        }else{

            int middle = (start + end) / 2;

            int[] left = plusGrdeSommeRec3(arr,n, start, middle);
            int[] right = plusGrdeSommeRec3(arr,n, middle + 1, end);
            int[] croise = trouverSousSequenceCroiseMax(arr, start, middle, end);

            if (left[0] >= right[0] && left[0] >= croise[0]) {
                result[0] = left[0];
                result[1] = left[1];
                result[2] = left[2];
            } else if (right[0] >= left[0] && right[0] >= croise[0]) {
                result[0] = right[0];
                result[1] = right[1];
                result[2] = right[2];
            } else {
                result[0] = croise[0];
                result[1] = croise[1];
                result[2] = croise[2];
            }
        }
        return result;
    }

    /**
     * 
     * @param arr
     * @param start
     * @param middle
     * @param end
     * @return
     */
    int[] trouverSousSequenceCroiseMax(int[] arr, int start, int middle, int end) {
        int sumLeftMax = 0;
        int sum = 0;
        int left = 0;

        for (int i = middle; i >= start; i--) {
            sum += arr[i];
            cpt++; //!do not count
            if (sum > sumLeftMax) {
                sumLeftMax = sum;
                left = i;
            }
        }

        int sumRightMax = 0;
        sum = 0;
        int right = 0;

        for (int i = middle + 1; i <= end; i++) {
            sum += arr[i];
            if (sum > sumRightMax) {
                sumRightMax = sum;
                right = i;
            }
        }

        int[] result = {sumLeftMax+sumRightMax,left,right};

        return result;
    }

    /**
     * 
     * @param arr
     * @param n
     * @return
     */
    int[] plusGrdeSomme4(int[] arr,int n){
        int[] result = new int[3];
        int[] S = new int[n];
        int valMax;
        boolean onlyNegatives = true;
        int indStart = 0;
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
                indStart = m;
                break;
            }
        }
        result[1] = indStart;

        if(onlyNegatives){
            result[0] = 0;
            result[1] = 0;
            result[2] = 0;
        }

        
        return result;
    }

    // test function
    
    /**
     * test of the plusGrdeSomme1()
     */
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
    
    /**
     * test of the plusGrdeSomme2()
     */
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

    /**
     * test of the plusGrdeSomme3()
     */
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

    /**
     * test of the plusGrdeSomme4()
     */
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


//test case function

    /**
     * test case of plusGrdeSomme1()
     * @param arr the array
     * @param n the size of the array
     * @param expect the expected value
     */
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
    /**
     * test case of plusGrdeSomme2()
     * @param arr the array
     * @param n the size of the array
     * @param expect the expected value
     */
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
    /**
     * test case of plusGrdeSomme3()
     * @param arr the array
     * @param n the size of the array
     * @param expect the expected value
     */
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
    /**
     * test case of plusGrdeSomme4()
     * @param arr the array
     * @param n the size of the array
     * @param expect the expected value
     */
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
    /**
     * test the effiency of plusGrdeSomme1
     */
    void testPlusGrdeSomme1Efficacite(){
        int maxN = (int) Math.pow(2, 10);
        int[] arr = new int[maxN];
        int min = -50, max = 50;
        long t1,t2,deltaT;
        double k;
        for(int i =0; i < maxN;i++){
            arr[i] = min + (int)(Math.random() * ((max - min) + 1));
        }

        for(int n = 2; n <= maxN; n*=2){
            cpt=0;
            t1 = System.nanoTime();
            plusGrdeSomme1(arr, n);
            t2 = System.nanoTime();
            deltaT = t2-t1;

            System.out.println("n="+n);
            System.out.println("Temps="+deltaT+"ns");
            System.out.println("cpt="+cpt);
            k = cpt/(double) (n*n*n);
            System.out.println("cpt/n^3 = "+(double) (k));
            System.out.println("-----------");
        }

    }
    /**
     * test the effiency of plusGrdeSomme2
     */
    void testPlusGrdeSomme2Efficacite(){


    }
    /**
     * test the effiency of plusGrdeSomme3
     */
    void testPlusGrdeSomme3Efficacite(){
        int maxN = (int) Math.pow(2, 8);
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
            plusGrdeSomme3(arr, n);
            t2 = System.nanoTime();
            deltaT = t2-t1;
            System.out.println("n="+n);
            System.out.println("Temps="+deltaT+"ns");
            System.out.println("cpt="+cpt);
            nlog2n = n*(Math.log10(n) / Math.log10(2));
            System.out.println("cpt/nlog2n = "+(double) (cpt/nlog2n));
            System.out.println("-----------");
        }

    }

    /**
     * test the effiency of plusGrdeSomme1
     */
    void testPlusGrdeSomme4Efficacite(){


    }
}
