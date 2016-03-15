# Backbase Training Exercise

## Portal Frontend - Module 2

### Exercise 2

_note: This exercise is 3 of 5 in a series of exercises that follow on from each other and should not be used independently_
_note: All exercises use the [standard portal set-up for backbase trainings](https://my.backbase.com/resources/how-to-guides/getting-your-first-launchpad-based-portal-set-up/)_

#### Description

In this exercise, you will learn how to extend the default ICE widget by creating your own ICE templates.

#### Steps

 - Create a template named **media-object.html**, based on the [Twitter Bootstrap Media Object](http://getbootstrap.com/components/#media)
 - Upload your custom content template to the Enterprise Catalog
 - Once the template is added to the Enterprise Catalog, it can be consumed by a Content Widget in one of the following two ways:
  - use the content widget provided with the exercise (link to readme from the widget) (Should be zipped and dragged to the portal catalog)
  - In portal catalog, access the content widget settings and switch to the properties tab. Add the **,Media Object,$(contextRoot)/static/features/[BBHOST]/media-object-content-template/media-object-template/media-object.html** at the end of the templateList property value.

#### References

 - [Create an ICE Widget](https://my.backbase.com/docs/product-documentation/documentation/portal/5.6.1/icewidgets_customize.html)
 - [Media Object](http://getbootstrap.com/components/#media)
 - [Twitter Bootstrap](http://getbootstrap.com/)
