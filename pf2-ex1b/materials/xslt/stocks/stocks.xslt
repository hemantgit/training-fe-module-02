<?xml version="1.0" encoding="UTF-8"?>

<!-- Simple transform of Connexys to HTML -->
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs">
	
	<xsl:output media-type="html" encoding="UTF-8" omit-xml-declaration="yes" />

	<xsl:template match='/'>
		<div>
			<h3>Stock information</h3>
			<table class="jobs" cellspacing="0" border="0" cellpadding="0"
				summary="Stock information">
				<caption>Stock information</caption>
				<thead>
					<tr>
						<th scope="col">Symbol</th>
						<th scope="col">Last</th>
						<th scope="col">High</th>
						<th scope="col">Low</th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each select="stocks/stock">
						<tr>
							<td scope="row">
								<xsl:value-of select="@symbol" />
							</td>
							<td scope="row">
								<xsl:value-of select="last" />
							</td>
							<td scope="row">
								<xsl:value-of select="high" />
							</td>
							<td scope="row">
								<xsl:value-of select="low" />
							</td>
						</tr>
					</xsl:for-each>
				</tbody>
			</table>
		</div>
	</xsl:template>
</xsl:stylesheet>