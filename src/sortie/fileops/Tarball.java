package sortie.fileops;

import java.io.FileInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;

import com.ice.tar.TarEntry;

import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;


/**
 * Functions for working with tarballs (.gz.tar files).
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>October 1, 2007: Improvements to use cross-system files
 * <br>October 26, 2007: Updated to be able to work around the \@longlink
 * naming problem - the inability of older versions of TAR (such as ours) to
 * handle long filenames (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)\
 * <br>February 22, 2011: Corrected a bug relating to opening files without
 * an associated path (LEM)
 */

public class Tarball {
  /**
   * Extracts a tarball's entries into the designated temp folder.  If there is
   * file info in the tar archive, they automatically get put in a sub folder
   * of the root directory.
   * @param sTarball Tarball to extract
   * @param sTempRoot Temp root directory
   * @return File where the files were extracted to.
   * @throws ModelException wraps IOExceptions.
   */
  public static String extractTarball(String sTarball, String sTempRoot) throws
  ModelException {
    FileInputStream jFileInputStream = null;
    try {

      //Find out what directory the output files will be written to
      jFileInputStream = new FileInputStream(sTarball);
      TarArchive oTarball = new TarArchive(jFileInputStream);
      String sSeparator = System.getProperty("file.separator"), 
          sDir = oTarball.getPath();

      //Work with either type of separator  
      if (sDir.lastIndexOf('\\') >= 0) {
        if (sDir.startsWith("\\") == false)
          sDir = '\\' + sDir;
        sDir = sTempRoot + sDir + sSeparator;
      } else if (sDir.lastIndexOf('/') >= 0) {
        if (sDir.startsWith("/") == false)
          sDir = '/' + sDir;
        sDir = sTempRoot + sDir + sSeparator;
      } else if (sDir.length() == 0 || sDir.indexOf(".xml.gz") >= 0) {
        //No path - but sometimes tar returns the filename in this case
        sDir = sTempRoot + sSeparator;  
      } else {
        //Path only one folder deep, no separator
        sDir = sTempRoot + sSeparator + sDir + sSeparator;
      }

      //Extract the contents to the root - they'll end up in m_sTempDir
      //Reset the tar archive - checking the path messed up the tar entries
      jFileInputStream.close();
      jFileInputStream = new FileInputStream(sTarball);
      oTarball = new TarArchive(jFileInputStream);
      oTarball.setKeepOldFiles(false); //overwrite anything already there
      oTarball.extractContents(new File(sTempRoot + sSeparator));
      oTarball.closeArchive();

      return sDir;
    }
    catch (IOException e) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "ExtractTarball", 
          "SORTIE could not open file " + sTarball + ". Message: " +
              e.getMessage()));
    }
    finally {
      try {
        if (jFileInputStream != null) {
          jFileInputStream.close();
        }
      }
      catch (IOException e) {
        ; // do nothing
      }
    }
  }

  /**
   * Extracts a tarball's entries into the designated temp folder.  If there is
   * file info in the tar archive, they automatically get put in a sub folder
   * of the root directory.
   * @param sTarball Tarball to extract
   * @param sTempRoot Temp root directory
   * @return File where the files were extracted to.
   * @throws ModelException wraps IOExceptions.
   */
  public static String extractTarballNoPath(String sTarball, String sTempRoot) throws
  ModelException {
    FileInputStream jFileInputStream = null;
    try {

      //Find out what directory the output files will be written to
      jFileInputStream = new FileInputStream(sTarball);
      TarArchive oTarball = new TarArchive(jFileInputStream);
      String sSeparator = System.getProperty("file.separator"), 
          sDir = oTarball.getPath();

      //Work with either type of separator  
      if (sDir.lastIndexOf('\\') >= 0) {
        if (sDir.startsWith("\\") == false)
          sDir = '\\' + sDir;
        sDir = sTempRoot + sDir + sSeparator;
      } else if (sDir.lastIndexOf('/') >= 0) {
        if (sDir.startsWith("/") == false)
          sDir = '/' + sDir;
        sDir = sTempRoot + sDir + sSeparator;
      } else if (sDir.length() == 0 || sDir.indexOf(".xml.gz") >= 0) {
        //No path - but sometimes tar returns the filename in this case
        sDir = sTempRoot + sSeparator;  
      } else {
        //Path only one folder deep, no separator
        sDir = sTempRoot + sSeparator + sDir + sSeparator;
      }

      //Extract the contents to the root - they'll end up in m_sTempDir
      //Reset the tar archive - checking the path messed up the tar entries
      jFileInputStream.close();
      jFileInputStream = new FileInputStream(sTarball);
      oTarball = new TarArchive(jFileInputStream);
      oTarball.setKeepOldFiles(false); //overwrite anything already there
      oTarball.extractContentsNoPath(new File(sTempRoot + sSeparator));
      oTarball.closeArchive();

      return sDir;
    }
    catch (IOException e) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "extractTarballNoPath", 
          "SORTIE could not open file " + sTarball + ". Message: " +
              e.getMessage()));      
    }
    finally {
      try {
        if (jFileInputStream != null) {
          jFileInputStream.close();
        }
      }
      catch (IOException e) {
        ; // do nothing
      }
    }
  }

  /**
   * This converts a gzipped file to an uncompressed file stream.
   * @param sFileToGet - the file to uncompress.
   * @return - InputStream - the uncompressed file stream
   * @throws ModelException if the file is not found, or wrapping another
   * exception.
   */
  public static InputStream getUnzipFileStream(String sFileToGet) throws
  ModelException {
    try {
      File oFile = new File(sFileToGet);
      FileInputStream oFileInputStream = new FileInputStream(oFile);
      GZIPInputStream oGZipStream = new GZIPInputStream(oFileInputStream);
      //GZIPInputStream oGZipStream = new GZIPInputStream(new FileInputStream(new
       //   File(sFileToGet)));

      return oGZipStream;

    }
    catch (IOException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA", e.getMessage()));
    }
  }

  /** 
   * Creates a tarball from a set of files. 
   * @param sTarball Tarball to create
   * @param p_sFiles Files to put into the tarball
   * @throws ModelException 
   */
  public static void makeTarball(String sTarball, String[] p_sFiles) throws ModelException {
    FileOutputStream jOutStream = null;
    TarArchive oTarball = null; 

    try {
      jOutStream = new FileOutputStream(sTarball);
      oTarball = new TarArchive(jOutStream);

      for (String string : p_sFiles) {
        String sZipped = zipFile(string);
        File f = new File( sZipped );

        TarEntry entry = new TarEntry( f );
        entry.setUSTarFormat();
        oTarball.writeEntry( entry, true );  
      }

      jOutStream.flush();
      oTarball.closeArchive();

    } catch (IOException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA", e.getMessage()));
    } finally {
      try {
        if (jOutStream != null) jOutStream.close();        
      } catch (IOException e) {;}
    }
  }

  /**
   * Unzips a file. As with gzip, this will delete the original file.
   * @param sFile File to unzip.
   * @return Path and file name of unzipped file.
   * @throws ModelException If there is a problem writing the file.
   */
  public static String unzipFile(String sFile) throws ModelException {
    FileInputStream jInFileStream = null;
    FileOutputStream jOutFileStream = null;
    GZIPInputStream jGZipStream = null;
    String sFileToWrite = sFile;
    boolean bSuccessful = false;
    try {      
      if (sFileToWrite.endsWith(".gz")) 
        sFileToWrite = sFileToWrite.substring(0, (sFileToWrite.length()-3));
      jInFileStream = new FileInputStream(sFile);
      jGZipStream = new GZIPInputStream(jInFileStream);
      jOutFileStream = new FileOutputStream(sFileToWrite);

      // Transfer bytes from the gzip input file 
      // to the output stream
      byte[] buf = new byte[1024];
      int len;
      while ((len = jGZipStream.read(buf)) > 0) {
        jOutFileStream.write(buf, 0, len);
      }           
      bSuccessful = true;      
    }
    catch (IOException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA", e.getMessage()));
    }
    finally {
      try {
        jGZipStream.close();
        jOutFileStream.close();
        jInFileStream.close();
        if (bSuccessful) new File(sFile).delete();
      } catch (IOException e) {;}
    }
    return sFileToWrite;
  }

  /**
   * Zips a file. As with gzip, this will delete the original file.
   * @param sFile File to zip.
   * @return File name of the zipped file.
   * @throws ModelException If something goes wrong in the zipping.
   */
  public static String zipFile(String sFile) throws ModelException {
    String sFileToWrite = sFile + ".gz";
    FileInputStream jInFileStream = null;
    FileOutputStream jOutFileStream = null;
    GZIPOutputStream jGZipStream = null;
    boolean bSuccessful = false;
    try {      
      jOutFileStream = new FileOutputStream(sFileToWrite);
      jGZipStream = new GZIPOutputStream(jOutFileStream);

      jInFileStream = new FileInputStream(sFile);       

      // Transfer bytes from the input file 
      // to the gzip output stream
      byte[] buf = new byte[1024];
      int len;
      while ((len = jInFileStream.read(buf)) > 0) {
        jGZipStream.write(buf, 0, len);
      }
      // Finish creation of gzip file
      jGZipStream.finish();     
      bSuccessful = true;
    }
    catch (IOException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA", e.getMessage()));
    }
    finally {
      try {
        if (jGZipStream != null) jGZipStream.close();
        if (jOutFileStream != null) jOutFileStream.close();
        if (jInFileStream != null) jInFileStream.close();
        if (bSuccessful) new File(sFile).delete();
      } catch (IOException e) {;}
    }
    return sFileToWrite;
  }

  /**
   * Extracts a file from .gz.tar land to plaintext.  The file will be placed
   * in the same directory as the tarball.
   * @param sTarball Tarball in which the file is.
   * @param sFileName Name of file to extract, with no path information and
   * with no .gz or .tar extension (but with its plaintext extension).
   * @return The full filename and path of the extracted file, or null if the
   * file was not found in the tarball.
   * @throws ModelException if the file is bad.
   */
  public static String extractTarballFile(String sTarball, String sFileName) throws
  ModelException {
    FileInputStream jInStream = null;
    FileInputStream jZipFileStream = null;
    GZIPInputStream jGZipStream = null;
    try {

      if (sFileName == null || sFileName.length() == 0) {
        return null;
      }

      String sExtractedFile = null;
      jInStream = new FileInputStream(sTarball);
      TarArchive oTarball = new TarArchive(jInStream);
      String sSeparator = System.getProperty("file.separator"), 
          sFilePath = sTarball.substring(0, sTarball.lastIndexOf(sSeparator));

      sExtractedFile = oTarball.extractFile(new File(sFilePath), sFileName);

      //Now unzip the file
      //Open the GZIP stream with the file still having the .gz extension
      File jZipFile = new File(sExtractedFile);
      jZipFileStream = new FileInputStream(jZipFile);
      jGZipStream = new GZIPInputStream(jZipFileStream);
      sExtractedFile = sFilePath + sSeparator + sFileName;
      FileOutputStream jUnzippedOutFile = new FileOutputStream(sExtractedFile);
      int c;
      while ( (c = jGZipStream.read()) != -1) {
        jUnzippedOutFile.write(c);
      }
      jUnzippedOutFile.close();
      jUnzippedOutFile = null;
      jGZipStream.close();
      jGZipStream = null;
      jZipFileStream.close();
      jZipFileStream = null;

      //Delete the gzip file, if it's still there
      File oTemp = new File(sExtractedFile + ".gz");
      oTemp.delete();

      return sExtractedFile;

    }
    catch (IOException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "ExtractTarballFile",
          e.getMessage()));
    }
    finally {
      try {
        if (jInStream != null) 
          jInStream.close();
        if (jZipFileStream != null) 
          jZipFileStream.close();
        if (jGZipStream != null) 
          jGZipStream.close();
      }
      catch (IOException e) {
        ;
      }
    }
  } 

  /**
   * Extracts a file from .gz.tar land to plaintext.  The file will be placed
   * in the chosen directory.
   * @param sTarball Tarball in which the file is.
   * @param sFileName Name of file to extract.
   * @param sDestinationPath Path to extract to.
   * @return The full filename and path of the extracted file, or null if the
   * file was not found in the tarball.
   * @throws ModelException if the file is bad.
   */
  public static String extractTarballFileToPath(String sTarball, 
      String sFileName, String sDestinationPath) throws
      ModelException {
    FileInputStream jInStream = null;
    FileInputStream jZipFileStream = null;
    GZIPInputStream jGZipStream = null;
    try {

      if (sFileName == null || sFileName.length() == 0) {
        return null;
      }

      String sExtractedFile = null;
      jInStream = new FileInputStream(sTarball);
      TarArchive oTarball = new TarArchive(jInStream);
      String sSeparator = System.getProperty("file.separator");

      sExtractedFile = oTarball.extractFileNoPath(new File(sDestinationPath), sFileName);

      //Now unzip the file
      //Open the GZIP stream with the file still having the .gz extension
      File jZipFile = new File(sExtractedFile);
      jZipFileStream = new FileInputStream(jZipFile);
      jGZipStream = new GZIPInputStream(jZipFileStream);
      sFileName = sFileName.replace('/', File.separatorChar);
      sExtractedFile = sDestinationPath + sSeparator + sFileName.substring(sFileName.lastIndexOf(File.separatorChar) + 1);
      FileOutputStream jUnzippedOutFile = new FileOutputStream(sExtractedFile);
      int c;
      while ( (c = jGZipStream.read()) != -1) {
        jUnzippedOutFile.write(c);
      }
      jUnzippedOutFile.close();
      jUnzippedOutFile = null;
      jGZipStream.close();
      jGZipStream = null;
      jZipFileStream.close();
      jZipFileStream = null;

      //Delete the gzip file, if it's still there
      File oTemp = new File(sExtractedFile + ".gz");
      oTemp.delete();

      return sExtractedFile;

    }
    catch (IOException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "ExtractTarballFile",
          e.getMessage()));
    }
    finally {
      try {
        if (jInStream != null) 
          jInStream.close();
        if (jZipFileStream != null) 
          jZipFileStream.close();
        if (jGZipStream != null) 
          jGZipStream.close();
      }
      catch (IOException e) {
        ;
      }
    }
  } 

  /**
   * Deletes all files from the tarball in the temp folder.
   * @param sTarball Tarball with the files to delete.
   * @param sTempDir Directory from which files should be cleaned
   * @param sTempRoot Temp directory root
   */
  public static void cleanUp(String sTarball, String sTempDir, String sTempRoot)
      throws ModelException {
    FileInputStream jFileInputStream = null;
    try {
      jFileInputStream = new FileInputStream(sTarball);
      TarArchive oTarball = new TarArchive(jFileInputStream);
      ArrayList<String> p_jFiles = oTarball.listContentsAsString();
      File jFile;
      String sTemp;
      String sSeparator = System.getProperty("file.separator");
      int i;

      for (i = 0; i < p_jFiles.size(); i++) {
        sTemp = p_jFiles.get(i);
        if (sTemp.startsWith(sSeparator)) {
          jFile = new File(sTempRoot + sTemp);
        }
        else {
          jFile = new File(sTempRoot + sSeparator + sTemp);
        }
        //if (!jFile.delete()) {
        //  JOptionPane.showMessageDialog(null, "Couldn't delete file " + jFile.getAbsolutePath());
        //	}
        jFile.delete();
      }

      //If the temp directory is different from the file root, delete it too
      if (!sTempDir.equals(sTempRoot + sSeparator)) {
        while (!sTempDir.equals(sTempRoot)) {
          jFile = new File(sTempDir);
          jFile.delete();
          sTempDir = jFile.getParent();
        }
      }
    }
    catch (IOException e) {
      //Do nothing
      throw (new ModelException(ErrorGUI.BAD_DATA, "Tarball.Cleanup", e.getMessage()));
      //JOptionPane.showMessageDialog(null, "IOException! " + e.getMessage());
    }
    finally {
      try {
        if (jFileInputStream != null) {
          jFileInputStream.close();
        }
      }
      catch (IOException e) {
        ; //do nothing
      }
    }
  }

  /**
   * Gets a list of file entries in the tarball.  The list is of the core
   * XML files, with no paths attached.
   * @param sTarball Tarball for which to get entries.
   * @return An array of filenames in the tarball, no path info, no .gz
   * extension, or null if the tarball is empty.
   * @throws ModelException if there is a problem reading the file.
   */
  public static String[] getTarballEntries(String sTarball) throws
  ModelException {
    FileInputStream oFileInputStream = null;
    try {

      oFileInputStream = new FileInputStream(sTarball);
      TarArchive oTarball = new TarArchive(oFileInputStream);
      ArrayList<String> p_sTempNames = oTarball.listContentsAsString();
      String[] p_sNames = new String[p_sTempNames.size()];
      String sSeparator = System.getProperty("file.separator"), sFileName;
      int i;

      if (p_sTempNames == null || p_sTempNames.size() == 0) return null;

      //Loop through all the tar entries and extract the filenames
      for (i = 0; i < p_sNames.length; i++) {

        sFileName = p_sTempNames.get(i);

        //Separate out the filename from any path information
        sFileName = sFileName.substring(sFileName.lastIndexOf(sSeparator) + 1);

        //Remove the ".gz" if it's there
        sFileName = sFileName.substring(0, sFileName.indexOf(".gz"));

        p_sNames[i] = sFileName;
      }

      return p_sNames;
    }
    catch (IOException e) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "getTarballEntries", 
          "SORTIE could not open file " + sTarball + ". Message: " +
              e.getMessage()));      
    }
    finally {
      if (oFileInputStream != null) {
        try {
          oFileInputStream.close();
        } catch (IOException e2) {;}
      }
    }
  }  
}
