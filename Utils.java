/**
 *Utils.java
 *
 *@author Alexander R. Cavaliere
 *
 *Contains the encryption and decryption methods for Letter RC4 and the character
 *conversion algorithm
 */

/**
 *Utils Contains the necessary functions to allow for the encryption and decryption
 *of English letters via RC4.
 */

public class Utils
{
    //internal state
    private int alpha;
    private int beta;

    /**
     *Utils Overriden default constructor. Sets alpha and beta to 0
     *for use in RC4
     */
    
    public Utils()
    {
        alpha = 0;
        beta = 0;
    }

    /**
     *converter Takes in a char and converts it to an integer
     *representing its place in the alphabet (0-25)
     *@param incoming
     *@return The converted char
     *@return -1 A non letter character
     */
    
    private int converter(char incoming)
    {
        int value = (char)incoming;

        if(value >= 65 && value <= 90)
            {
                return value - 65;
            }
        else if (value >= 97 && value <= 122)
            {
                return value - 97;
            }
        return -1;
    }//end converter

    /**
     *keyConversion Converts the key into an integer representation where each
     *integer is the character's place in the alphabet or -1 if not a letter
     *@param key The character array that is the key
     *@return convertedKey The converted key
     */
    
    public int[] keyConversion(char[] key)
    {
        int [] convertedKey = new int[key.length];
        for(int index = 0; index < convertedKey.length; index++)
            {
                convertedKey[index] = converter(key[index]);
            }
        return convertedKey;
    }//end keyConversion

    /**
     *converter The overloaded converter method that instead takes 
     *integer values read in by a FileInputStream from a file
     *and converts them into a value which represents their place
     *in the alphabet
     *@param value The incoming integer
     *@return The converted integer
     *@return -1 If it is a non letter representing integer
     */
    
    private int converter(int value)
    {
        if(value >= 65 && value <= 90)
            {
                return value - 65;
            }
        else if (value >= 97 && value <= 122)
            {
                return value - 97;
            }
        return -1;
    }//end converter

    /**
     *encrypt The encryption algorithm for a modified version of RC4 that goes by
     *"Letter RC4". This algorithm works just like RC4 but without a key scheduler
     *and it only encrpyts English letters
     *@param incoming An incoming pt value
     *@param key The super secret key
     *@return The encrypted value
     *@return -1 The incoming bit was a non english letter character
     */
    
    public int encrypt(int incoming, int[] key)
    {
        //Start RC4 PRNG Algo
        incoming = converter(incoming);
        if(incoming == -1)
            return -1;
                
        alpha = (alpha + 1) % 26;
        beta = (beta + key[alpha]) % 26;

        //Swap Part of RC4 PRNG Algo
        int temporary = key[alpha];
        key[alpha] = key[beta];
        key[beta] = temporary;

        //Generated bit of the Keystream
        int keyBit = key[(key[alpha] + key[beta]) % 26];

        //Incoming Plaintext Encrypted
        return (((incoming  + keyBit) % 26) + 65);
    }//end encrypt

    /**
     *decrypt The decryption algorithm for a modified version of RC4 that goes by
     *"Letter RC4". This algorithm works just like RC4 but without a key scheduler
     *and it only decrypts English letters
     *@param incoming The incoming ct value
     *@param key The super secret key
     *@return plaintext The decrypted value 
     *@return -1 The incoing bit was a non english letter character
     */
    
    public int decrypt(int incoming, int[] key)
    {
        incoming = converter(incoming);
        if(incoming == -1)
            return -1;
        //Start RC4 PRNG Algo
        alpha = (alpha + 1) % 26;
        beta = (beta + key[alpha]) % 26;

        //Swap Part of RC4 PRNG Algo
        int temporary = key[alpha];
        key[alpha] = key[beta];
        key[beta] = temporary;

        //Generated bit of the Keystream
        int keyBit = key[(key[alpha] + key[beta]) % 26];
        

        //Incoming Plaintext Encrypted
        int plaintext = (incoming  - keyBit) % 26;
        if(plaintext < 0)
            plaintext+=26;
        return plaintext + 65;
    }//end decrypt

    /**
     *argumentUsageError The usage message printed when the programs detects missing
     *arguments
     *@exception IllegalArgumentException Thrown to remind the user that not passing in
     *the required arguments is tantamont to criminal behaviour
     */
    
    public void argumentUsageError()
    {
        System.err.println("Usage: java Decrypt <SecretKey> <CiphertextFile> <PlaintextFile> ");
        System.err.println("Your secret key must be a permutation of the letters A/a - Z/z");
        System.err.println("The plaintext file must be in the current directory");
        System.err.println("The ciphertext file must be in the current directory");
        throw new IllegalArgumentException();
    }//end keyUsageError

    /**
     *keyUsageError The usage message printed when something is wrong with the super
     *secret key submitted by the user.
     *@exception IllegalArgumentException Thrown because the user was attempting to
     *pass in illegal values.
     */
    
    public void keyUsageError()
    {
        System.err.println("Usage: java Decrypt <SecretKey> <CiphertextFile> <PlaintextFile>");
        System.err.println("The secret key must be a permutation of the letters A/a - Z/z");
        System.err.println("Thus the secret key must be 26 characters long");
        throw new IllegalArgumentException();
    }//end keyUsageError
    
}//end Utils
