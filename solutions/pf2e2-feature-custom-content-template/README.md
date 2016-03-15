# Backbase Training Exercise

## Portal Frontend - Module 2

### Exercise 2

_note: This exercise is 3 of 5 in a series of exercises that follow on from each other and should not be used independently_
_note: All exercises use the [standard portal set-up for backbase trainings](https://my.backbase.com/resources/how-to-guides/getting-your-first-launchpad-based-portal-set-up/)_

#### Description

In this exercise, you will learn how to extend the default ICE widget by creating your own ICE templates.

#### Steps

 - Create a new ICE widget by extending the default Backbase ICE widget, following [this documentation](https://my.backbase.com/resources/documentation/portal/5.6.0/icewidgets_customize.html)
 - Create a template named **media-object.html**, based on the [Twitter Bootstrap Media Object](http://getbootstrap.com/components/#media)
 - Add this template in the ICE widget from the previous step and update the **model.xml** to make this new template the default one
 - Upload your custom Content Widget to the Portal Catalog and test that it works as expected

#### Additional resources

 - [Custom templates](../../templates/content/)
 - [Template styles](../../css/templates.css)
 - [server-catalog-widgets.xml](../../../../../config-info/import/server-catalog-widgets.xml#L53-L77)

#### References

 - [Create an ICE Widget](https://my.backbase.com/resources/documentation/portal/5.5.1.0/devd_tuto_ice_.html)
 - [Add a Content Template](https://my.backbase.com/resources/documentation/portal/5.5.1.0/devd_mang_icet_reftemp.html)
 - [Media Object](http://getbootstrap.com/components/#media)
 - [Twitter Bootstrap](http://getbootstrap.com/)




2 ways to do it

- use the content widget provided with the exercise (link to readme from the widget) (Should be zipped and dragged to the portal catalog)

- In portal catalog, access the content widget settings and switch to the properties tab. Add the **,Media Object,$(contextRoot)/static/features/[BBHOST]/media-object-content-template/media-object-template/media-object.html** at the end of the templateList property value.
