<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" />
    <xsl:param name="selectedCurrencies" />
    <xsl:template match="/">
        <table class="bbtutorials-item-list">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Link</th>
                    <th>Publication Date</th>
                    <th>Description</th>
                    <th>Category</th>
                </tr>
            </thead>
            <xsl:for-each select="rss/channel/item">
                <xsl:if test="not(string($selectedCurrencies)) or contains($selectedCurrencies,substring(title,1,3))">
                    <tr>
                        <td class="bbtutorials-item-field">
                            <xsl:value-of select="title"/>
                        </td>
                        <td class="bbtutorials-item-field">
                            <xsl:value-of select="link"/>
                        </td>
                        <td class="bbtutorials-item-field">
                            <xsl:value-of select="pubDate"/>
                        </td>
                        <td class="bbtutorials-item-field">
                            <xsl:value-of select="description"/>
                        </td>
                        <td class="bbtutorials-item-field">
                            <xsl:value-of select="category"/>
                        </td>
                    </tr>
                </xsl:if>
            </xsl:for-each>
        </table>
    </xsl:template>
</xsl:stylesheet>