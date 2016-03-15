# Backbase Training Exercise

## Portal Frontend - Module 2

### Exercise 4

_note: This exercise is 5 of 5 in a series of exercises that follow on from each other and should not be used independently_
_note: All exercises use the [standard portal set-up for backbase trainings](https://my.backbase.com/resources/how-to-guides/getting-your-first-launchpad-based-portal-set-up/)_

#### Description

In this exercise, you will learn how to create you own custom chrome for Backbase widgets.

#### Steps

 - Use Twitter Bootstrap Panels, with heading, footer, content
 - In the Heading, you must display the widget title
 - In the content, the widget itself
 - In the footer, the current limit of todos for the widget (if there is a limit)
 - Add this feature in the Enterprise Catalog.
 - Open `webapps/portalserver/src/main/resources/conf/uiEditingOptions.js` and add this line in widget chrome list
 ```
 {label: "Custom Chrome", value: "$(contextRoot)/static/features/[BBHOST]/custom-chrome-feature/custom-chrome/custom-chrome.html"}
 ```
 - Restart portal server
 - Test your new chrome with the notification widget you built

#### References

 - [Widget Chrome Development](https://my.backbase.com/docs/product-documentation/documentation/portal/5.6.1/widgets_chrome.html)
 - [Twitter Bootstrap Panels](http://getbootstrap.com/components/#panels)
 
 
