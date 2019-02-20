Feature: HighchartsAutomation
In order to compare the session count between tool tip and highlighted window
As a candidate
I want to navigate to the given link



Scenario: Comapare session count between tool tip and highlighted window
Given : Access to browser and url is given
When : Mouseover to Jan 5,2018 on the graph
Then : Store sessions count to a variable
And : Click on Jan 5,2018
Then : Comapare session count between tool tip and highlighted window