<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:dc="http://purl.org/dc/elements/1.1/" version="1.0">
    <xsl:output method="html"/>
    <xsl:template match="/">
        <div>
            <h3>
                <xsl:value-of select="/rss/channel/title" disable-output-escaping="yes"/>
            </h3>
            <p>
                <xsl:value-of select="/rss/channel/description" disable-output-escaping="yes"/>
            </p>
            <xsl:for-each select="/rss/channel/item">
                <h4>
                    <xsl:value-of select="title" disable-output-escaping="yes"/>
                </h4>
                <p>
                    <xsl:value-of select="description" disable-output-escaping="yes"/>
                </p>
                <p style="color: #ccc; font-size: 0.7em;">
                    <xsl:value-of select="dc:creator"/>
                </p>
                <p style="color: #ccc; font-style: italic; font-size: 0.7em;">
                    <xsl:value-of select="pubDate"/>
                </p>
            </xsl:for-each>
        </div>
    </xsl:template>
</xsl:stylesheet>
