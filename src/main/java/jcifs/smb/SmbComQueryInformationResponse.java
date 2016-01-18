/* jcifs smb client library in Java
 * Copyright (C) 2000  "Michael B. Allen" <jcifs at samba dot org>
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package jcifs.smb;


import java.util.Date;

import jcifs.Configuration;
import jcifs.util.Hexdump;


class SmbComQueryInformationResponse extends ServerMessageBlock implements Info {

    private int fileAttributes = 0x0000;
    private long lastWriteTime = 0L;
    private long serverTimeZoneOffset;
    private int fileSize = 0;


    SmbComQueryInformationResponse ( Configuration config, long serverTimeZoneOffset ) {
        super(config);
        this.serverTimeZoneOffset = serverTimeZoneOffset;
        this.command = SMB_COM_QUERY_INFORMATION;
    }


    @Override
    public int getAttributes () {
        return this.fileAttributes;
    }


    @Override
    public long getCreateTime () {
        return this.lastWriteTime + this.serverTimeZoneOffset;
    }


    @Override
    public long getLastWriteTime () {
        return this.lastWriteTime + this.serverTimeZoneOffset;
    }


    @Override
    public long getSize () {
        return this.fileSize;
    }


    @Override
    int writeParameterWordsWireFormat ( byte[] dst, int dstIndex ) {
        return 0;
    }


    @Override
    int writeBytesWireFormat ( byte[] dst, int dstIndex ) {
        return 0;
    }


    @Override
    int readParameterWordsWireFormat ( byte[] buffer, int bufferIndex ) {
        if ( this.wordCount == 0 ) {
            return 0;
        }
        this.fileAttributes = SMBUtil.readInt2(buffer, bufferIndex);
        bufferIndex += 2;
        this.lastWriteTime = SMBUtil.readUTime(buffer, bufferIndex);
        bufferIndex += 4;
        this.fileSize = SMBUtil.readInt4(buffer, bufferIndex);
        return 20;
    }


    @Override
    int readBytesWireFormat ( byte[] buffer, int bufferIndex ) {
        return 0;
    }


    @Override
    public String toString () {
        return new String(
            "SmbComQueryInformationResponse[" + super.toString() + ",fileAttributes=0x" + Hexdump.toHexString(this.fileAttributes, 4)
                    + ",lastWriteTime=" + new Date(this.lastWriteTime) + ",fileSize=" + this.fileSize + "]");
    }
}