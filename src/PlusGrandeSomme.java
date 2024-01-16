/**
 * biggest sum in a subsequence
 * @author Rayanne M., Maël C.
 */
class PlusGrandeSomme{
    /** counter of operation */
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
        testPlusGrdeSomme2Efficacite();
        testPlusGrdeSomme3Efficacite();
        testPlusGrdeSomme4Efficacite();
    }


    /**
     * efficiency in Θ(n^3)
     * @param arr the array 
     * @param n the length of the array
     * @return an array as [max,first index, last index]
     */
    int[] plusGrdeSomme1(int[] arr,int n){
        //![max,ind1,ind2]
        //* add = max
        //* index = ind1
        //* index+lengthSubsequence-1 = ind2 or the index + its length - 1
        int[] result = new int[3]; //! 1
        int add = 0; //! 2
        boolean onlyNegatives = true; //! 2
        //! 2 
        for(int index = 1; index < n; index++){ //! nbT1(1
            //! 2
            if(arr[index] > 0) onlyNegatives = false; //! 1
            // subsequence size
            //! 2
            for(int lengthSubsequence = 0; lengthSubsequence < n-index; lengthSubsequence++){//! nbT2(2
                //! 2
                //! 2
                for(int iii = 0;iii < lengthSubsequence ;iii++){ //! nbT3(1
                    //! 2
                    cpt++; //!do not count
                    add += arr[index+iii];//! 3
                }//!)

                if(add > result[0]){ //! 1
                    result[0] = add; //! 1
                    result[1] = index; //! 1
                    result[2] = index + lengthSubsequence-1; // -1 because of the length //! 3
                }
                add = 0; //! 1
            }//! )
        }//! )
        
        if(onlyNegatives){ //! 1
            result[0] = 0;
            result[1] = 0;
            result[2] = 0;
        }
        
        return result;
    }
    

    /**
     * efficiency in Θ(n^2)
     * @param arr the array
     * @param n the size of the array
     * @return an array as [max,first index, last index]
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
                cpt++;
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
     * @return an array as [max,first index, last index]
     */
    int[] plusGrdeSomme3(int[] arr, int n){
        //![max,ind1,ind2]
        int ind1 = 0,ind2 =n-1;
        int[] result = new int[3];
        
        result = plusGrdeSommeRec3(arr,n,ind1,ind2);

        return result;
    }

    /**
     * the recursive function of plusGrdeSomme3
     * @param arr the array
     * @param n the size 
     * @param start the first index
     * @param end the last index
     * @return an array as [max,first index, last index]
     */
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
     * find the biggest subsequence when it's on both side
     * @param arr the array
     * @param start the first index
     * @param middle the middle index
     * @param end the last index 
     * @return an array as [max,first index, last index]
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
     * efficiency in Θ(n)
     * @param arr the array
     * @param n the size of the array
     * @return an array as [max,first index, last index]
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
            cpt++;
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
     * test of plusGrdeSomme1()
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
        
    }
    
    /**
     * test of plusGrdeSomme2()
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
        

    }

    /**
     * test of plusGrdeSomme3()
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
        

    }

    /**
     * test of plusGrdeSomme4()
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

//test efficiency
    /**
     * test the efficiency of plusGrdeSomme1
     */
    void testPlusGrdeSomme1Efficacite(){
        int maxN = (int) Math.pow(2, 8);
        int[] arr = new int[maxN];
        int min = -50, max = 50;
        long t1,t2,deltaT;
        for(int i =0; i < maxN;i++){
            arr[i] = min + (int)(Math.random() * ((max - min) + 1));
        }

        for(int n = 2; n <= maxN; n*=2){
            cpt=0;
            t1 = System.nanoTime();
            plusGrdeSomme1(arr, n);
            t2 = System.nanoTime();
            deltaT = t2-t1;
            System.out.println("Temps="+deltaT+"ns");
            System.out.println("cpt="+cpt);
            System.out.println("cpt/n^3 = "+(double) (cpt/Math.pow(n, 3)));
            System.out.println(n*n*n);
            System.out.println("-----------");
        }

    }
    /**
     * test the efficiency of plusGrdeSomme2
     */
    void testPlusGrdeSomme2Efficacite(){
        int maxN = (int) Math.pow(2, 8);
        int[] arr = new int[maxN];
        int min = -50, max = 50;
        long t1,t2,deltaT;
        for(int i =0; i < maxN;i++){
            arr[i] = min + (int)(Math.random() * ((max - min) + 1));
        }

        for(int n = 2; n <= maxN; n*=2){
            cpt=0;
            t1 = System.nanoTime();
            plusGrdeSomme2(arr, n);
            t2 = System.nanoTime();
            deltaT = t2-t1;
            System.out.println("Temps="+deltaT+"ns");
            System.out.println("cpt="+cpt);
            System.out.println("cpt/n^2 = "+(double) (cpt/Math.pow(n, 2)));
            System.out.println("-----------");
        }

    }
    /**
     * test the efficiency of plusGrdeSomme3
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
     * test the efficiency of plusGrdeSomme1
     */
    void testPlusGrdeSomme4Efficacite(){
        int maxN = (int) Math.pow(2, 16);
        int[] arr = new int[maxN];
        int min = -50, max = 50;
        long t1,t2,deltaT;
        for(int i =0; i < maxN;i++){
            arr[i] = min + (int)(Math.random() * ((max - min) + 1));
        }

        for(int n = 2; n <= maxN; n*=2){
            cpt=0;
            t1 = System.nanoTime();
            plusGrdeSomme4(arr, n);
            t2 = System.nanoTime();
            deltaT = t2-t1;
            System.out.println("Temps="+deltaT+"ns");
            System.out.println("cpt="+cpt);
            System.out.println("cpt/n = "+(double) (cpt/n));
            System.out.println("-----------");
        }

    }
}
