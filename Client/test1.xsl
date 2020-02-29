<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                version="1.0">

    <xsl:template match="/">
        <html>
            <body>
                <h2>My Messages</h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>message</th>

                    </tr>
                    <xsl:for-each select="SingleChat/SingleChatMessage">
                        <tr>
                            <td>
                                <xsl:value-of select="content"/>

                            </td>

                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>