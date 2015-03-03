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
                        //RC4 Decryption
                        int plaintext = utilities.decrypt(incoming, convertedKey);
                        if(plaintext != -1)
                            {
                                //Ciphertext bit written to file
                                output.write(plaintext);
                            }
                    }
            }
        //Incase anything should happen the program stands ready
        catch(IOException io)
            {
                throw new IOException();
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
