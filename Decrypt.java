/**
 *Decrypt.java
 *
 *@author Alexander R. Cavaliere <arc6393@rit.edu>
 *
 *This program handles the decryption of inputted ciphertext that was
 *created by the implementation of the Letter RC4 algorithm found in
 *EncryptionUtils.java program. The output is written to a file as 
 *specified by the user.
 *
 *Version:
 *$Id: Decrypt.java,v 1.5 2015/02/26 20:58:28 arc6393 Exp $
 */


import java.io.*;

/**
 *Decrypt Handles the processing of user input, and then runs through a modified
 *RC4 decryption algorithm to decrypt the ciphertext into the plaintext that was
 *run through Encrypt.java (The accompaning program to this one) originally. The
 *format of the original however is not restored; solely the message.
 */

public class Decrypt
{
    /**
     *main Takes in user input, stores it, and then runs through the user input
     *character by character decrypting it and writing it to the specified 
     *output file. The message has no formatting, is solely composed of letters
     *and is in uppercase.
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
                System.err.println("Usage: java Decrypt <SecretKey> <CiphertextFile> <PlaintextFile> ");
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
        //Program Assumes Input is Valid From This Point On

        //User Input Processing
        try
            {
                //Files prepared for I/O
                input = new FileInputStream(args[1]);
                output = new FileOutputStream(args[2]);

                //Input file reading
                while((incoming = input.read()) != -1)
                    {   
                        //Checking for valid input (Just in case)
                        if(incoming >= 65 && incoming <= 90)
                            {
                                //Read bit is converted from UNICODE to Numeric Representation 0 - 25
                                incoming  = incoming - 65;

                                //Start of RC4 PRNG Algo
                                alpha = (alpha + 1) % 26;
                                beta = (beta + convertedKey[alpha]) % 26;

                                //Swap Portion of RC4 Algo
                                int temporary = convertedKey[alpha];
                                convertedKey[alpha] = convertedKey[beta];
                                convertedKey[beta] = temporary;

                                //Keystream bit generations
                                int keyBit = convertedKey[(convertedKey[alpha] + convertedKey[beta]) % 26];

                                //Decryption of incoming Ciphertext
                                int cipher = (incoming  -  keyBit) % 26;
                                
                                //Check if modulo is negative to add modulo
                                if(cipher < 0)
                                    {
                                        cipher += 26;
                                    }
                                
                                //Write Decrypted Plaintext
                                output.write(cipher + 65);
                            }
                        /*
                         *Should the input file contain invalid characters then
                         *that would indicate that the file was not encrypted
                         *with the Encrypt.java program that comes with this
                         *program.
                         */
                        else
                            {
                                System.err.println("Invalid Characters Detected!");
                                System.err.println("Please check that your input file is correct.");
                                throw new IOException();
                            }
                    }
            }
        //Should anything go wrong with the File IO
        catch(IOException io)
            {
                throw io;
            }
        
    }//end main
    
}//end Decrypt

/**
 *Revision:
 *$Log: Decrypt.java,v $
 *Revision 1.5  2015/02/26 20:58:28  arc6393
 *Uber Final Version
 *
 *Revision 1.4  2015/02/26 20:42:00  arc6393
 *Final Versions
 *
 *Revision 1.3  2015/02/23 21:02:59  arc6393
 *Finalized; More Testing Awaits!
 *
 *Revision 1.2  2015/02/23 19:18:21  arc6393
 *Need to move SimpleEncrypt and SimpleDecrypt into Encrypt and Decrypt; Add comments; Remove EncryptionUtils
 *
 *Revision 1.1  2015/02/23 04:05:50  arc6393
 *Decrypt is not working
 *
 */
