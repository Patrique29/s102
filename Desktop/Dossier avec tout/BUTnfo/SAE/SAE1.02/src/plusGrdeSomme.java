/**
 * plusGrdeSomme
 */
class plusGrdeSomme {
    long cpt;
    void principal(){
        testPlusGrdeSommen();

    }
    /**
     * plusGrandeSommeEnN allows you to find the subsequence with the largest sum
     * @param arr the array
     * @param n the length of the array
     * @return an array
     */
    int[] plusGrandeSommeEnN(int[] arr,int n){
        int[] result = new int[3];
        int[] S = new int[n]; //array with sum
        int sumMax; 
        int indStart = 0;
        int indEnd = 0;
        S[0] = arr[0];
        for(int i = 1; i<n; i++){
            if(S[i-1] <= 0){
                S[i] = arr[i];
            }
            else{
                S[i] = arr[i] + S[i - 1];
            }
        }
        sumMax = S[0];
        for(int j = 1; j<n; j++){
            if(sumMax < S[j]){
                sumMax = S[j];
                indEnd = j;
            }
        }
        result[0] = sumMax;
        result[2] = indEnd;
        for(int m = indEnd; m>0; m--){
            if(arr[m] == S[m]){
                indStart = m;
                break;
            }
        }
        result[1] = indStart;
        return arr;
    }

    void testPlusGrdeSommen(){
        int[] arr = {-1,8,-4,5,6,-9,-7,0,12};
        int[] result = plusGrandeSommeEnN(arr,9);
        System.out.print("La plus grande somme est : " + result[0] + " ind debut : "+ result[1] + " ind fin : " + result[2]);
        afficherTab(result, result.length);
    }

    void afficherTab(int[] var1, int var2) {
           System.out.println();
           for(int var3 = 0; var3 < var2; ++var3) {
              System.out.println("tab[" + var3 + "] = " + var1[var3]);
           }
           System.out.println();
        }
  
}