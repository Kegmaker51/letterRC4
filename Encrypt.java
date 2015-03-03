/**
 *Encrypt.java
 *
 *@author Alexander R. Cavaliere <arc6393@rit.edu>
 *
 *An implementation of the RC4 encryption algorithm. This specifically
 *implements the Letter-RC4 version. The program takes in a key, plaintext
 *file, and a cryptotext file.
 *
 *Version:
 *$Id: Encrypt.java,v 1.8 2015/02/26 20:58:29 arc6393 Exp $
 */

import java.io.*;

/**
 *Encrypt Goes through the necessary process to encrypt given plaintext
 *files with the Letter-RC4 encryption algorithm. Handles user input, processing
 *, and output. The plaintext will lose all formating and will be saved
 *in uppercase upon being written.
 */

public class Encrypt
{
    /**
     *main Runs the encryption process. Takes in user input (validating it as well)
     *and then performs a modified RC4 procedure on the plaintext.
     *The newly created ciphertext is then written to a file (Specified by the user).
     *@param args The command line arguments
     *@exception IllegalArgumentException Thrown if arguments are missing or the
     *                            the key is invalid
     *@excpetion io IOException thrown should anything go wrong when reading or 
     *           writing from or to the user input files.
     */
    
    public static void main(String[] args) throws Exception
    {           
        //Input checking begins here
        if(args.length != 3)
            {
                System.err.println("Usage: java Encrypt <SecretKey> <PlaintextFile> <CiphertextFile> ");
                System.err.println("Your secret key must be a permutation of the letters A/a - Z/z");
                System.err.println("The plaintext file must be in the current directory");
                System.err.println("The ciphertext file must be in the current directory");
                throw new IllegalArgumentException();
            }

        FileInputStream input = null;
        FileOutputStream output = null;

        char [] key = new char [args[0].length()];
        key = args[0].toCharArray();
                

        int incoming;

        //Needed for the RC4 algo
        int alpha = 0;
        int beta = 0;
                
        //Input testing variables
        Boolean badKey = false;
        int keySize = 0;
        String testing = new String(args[0]);
        
        int [] convertedKey = new int [args[0].length()];
        int keyConverted = 0;
        

        //Key Conversion from UNICODE to Integer Representations 0 - 25
        for(int index = 0; index < args[0].length(); index++)
            {
                int value = (char)key[index];

                if(value >= 48 && value <= 57)
                    {
                        badKey = true;
                        break;
                    }

                if(value >= 65 && value <= 90)
                    {
                        convertedKey[index] = value - 65;
                    }
                else if (value >= 97 && value <= 122)
                    {
                        convertedKey[index] = value - 97;
                    }

            }

        //Input Error Message (Errors specifically involving the key)
        if(badKey || keySize != 26)
            {
                System.err.println("Usage: java Decrypt <SecretKey> <CiphertextFile> <PlaintextFile>");
                System.err.println("The secret key must be a permutation of the letters A/a - Z/z");
                System.err.println("Thus the secret key must be 26 characters long");
                throw new IllegalArgumentException();
            }
        //Input Error Message (Errors specifically involving the key)
        if(args[0].length() != convertedKey.length || keySize != 26)
            {
                System.err.println("Usage: java Encrypt <SecretKey> <PlaintextFile> <CiphertextFile>");
                System.err.println("The secret key must be a permutation of the letters A/a - Z/z");
                System.err.println("Thus the secret key must be 26 characters long");
                throw new IllegalArgumentException();
            }
        
        //Program Assumes Valid Input Past This Point

        //Read in user inputted plaintext
        try
            {
                //User submitted files are prepared for I/O
                input = new FileInputStream(args[1]);
                output = new FileOutputStream(args[2]);
        
                while((incoming = input.read()) != -1)
                    {   
                        if(incoming >= 65 && incoming <= 90) 
                            {
                                //Letter is uppercase  A - Z
                                incoming  = incoming - 65;

                                //Start RC4 PRNG Algo
                                alpha = (alpha + 1) % 26;
                                beta = (beta + convertedKey[alpha]) % 26;

                                //Swap Part of RC4 PRNG Algo
                                int temporary = convertedKey[alpha];
                                convertedKey[alpha] = convertedKey[beta];
                                convertedKey[beta] = temporary;

                                //Generated bit of the Keystream
                                int keyBit = convertedKey[(convertedKey[alpha] + convertedKey[beta]) % 26];

                                //Incoming Plaintext Encrypted
                                int cipher = (incoming  + keyBit) % 26;

                                //Ciphertext bit written to file
                                output.write(cipher + 65);
                            }
                        else if (incoming >= 97 && incoming <= 122)
                            {
                                //Letter is lowercase a - z
                                incoming = incoming - 97;

                                //Start RC4 Algo
                                alpha = (alpha + 1) % 26;
                                beta = (beta + convertedKey[alpha]) % 26;

                                //Swap Part of Algo
                                int temporary = convertedKey[alpha];
                                convertedKey[alpha] = convertedKey[beta];
                                convertedKey[beta] = temporary;

                                //Generated bit of Keystream
                                int keyBit = convertedKey[(convertedKey[alpha] + convertedKey[beta]) % 26];

                                //Incoming Plaintext Encrypted
                                int cipher = (incoming  + keyBit) % 26;

                                //Ciphertext bit written to file
                                output.write(cipher + 65);
                            }
                    }
            }
        //Incase anything should happen the program stands ready
        catch(IOException io)
            {
                throw io;
            }

    }//end main

}//end Encrypt class

/**
 *Revision:
 *$Log: Encrypt.java,v $
 *Revision 1.8  2015/02/26 20:58:29  arc6393
 *Uber Final Version
 *
 *Revision 1.7  2015/02/26 20:42:00  arc6393
 *Final Versions
 *
 *Revision 1.6  2015/02/23 21:03:01  arc6393
 *Finalized; More Testing Awaits!
 *
 *Revision 1.5  2015/02/23 19:18:22  arc6393
 *Need to move SimpleEncrypt and SimpleDecrypt into Encrypt and Decrypt; Add comments; Remove EncryptionUtils
 *
 *Revision 1.4  2015/02/23 04:05:51  arc6393
 *Decrypt is not working
 *
 *Revision 1.3  2015/02/18 02:58:32  arc6393
 *Input and key handling works; needs more testing
 *
 *Revision 1.2  2015/02/17 02:35:16  arc6393
 *Finished Implementing File INPUT; Needs Testing; Currently need to work on taking keystream and applying it to Ciphertext Equation
 *
 *Revision 1.1  2015/02/15 23:39:40  arc6393
 *Initial Commit; Need to double check how to implement key stream generator; Needs Testing
 *
 *
 */
