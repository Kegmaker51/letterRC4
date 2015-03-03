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
        Utils utilities = new Utils();
        
        if(args.length != 3)
            {
                utilities.argumentUsageError();
            }
        FileInputStream input = null;
        FileOutputStream output = null;
        
        char [] key = new char [args[0].length()];
        key = args[0].toCharArray();
                
        int incoming;
                
        //Input testing variables
        Boolean badKey = false;
        int keySize = args[0].length();
        
        int [] convertedKey = new int [keySize];

        //Key Conversion from UNICODE to Integer Representations 0 - 25
        convertedKey = utilities.keyConversion(key);
        
        for(int index = 0; index < keySize; index++)
            if(convertedKey[index] == -1)
                {
                    badKey = true;
                    break;
                }
        
        //Input Error Message (Errors specifically involving the key)
        if(badKey || keySize != 26)
            {
                utilities.keyUsageError();
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
                        //RC4 Encryption
                        int cipher = utilities.encrypt(incoming, convertedKey);
                        if(cipher != -1)
                            {
                                //Ciphertext bit written to file
                                output.write(cipher);
                            }
                    }
            }
        //Incase anything should happen the program stands ready
        catch(IOException io)
            {
                throw new IOException();
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
