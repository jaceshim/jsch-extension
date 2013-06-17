package com.pastdev.jsch.file.spi;


import java.io.IOException;
import java.io.InputStream;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.jcraft.jsch.JSchException;


public class ScpFileInputStream extends InputStream {
    private static Logger logger = LoggerFactory.getLogger( ScpFileInputStream.class );

    private ScpDirectoryInputStream inputStream;
    private ScpEntry entry;

    ScpFileInputStream( ScpEntry entry ) throws JSchException, IOException {
        if ( !entry.isRegularFile() ) {
            throw new UnsupportedOperationException( "can only open file input stream on a regular file" );
        }
        logger.debug( "Opening ScpInputStream to {} {}", entry );
        this.inputStream = new ScpDirectoryInputStream( entry );
        this.scpEntry = this.inputStream.getNextEntry();
    }

    public String getMode() {
        return scpEntry.getMode();
    }

    public String getName() {
        return scpEntry.getName();
    }

    public long getSize() {
        return scpEntry.getSize();
    }

    @Override
    public void close() throws IOException {
        logger.debug( "Closing ScpInputStream" );
        inputStream.closeEntry();
        inputStream.close();
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }
}