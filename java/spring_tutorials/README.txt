this is a testing project of Spring framework, including its core project (IOC, AOP, etc) and some of the sub-projects.

The code presented in the project is roughly in line with the flow of the reference document, which is 5.0.3. as of this writing (Jan, 2018)
The code could be from other sources as well. In that case, I am trying to make it clear in the code's comment.

The project is constructed using maven and follow its best practice. Concretely speaking we have "main" and "test" on the high level of the src folder.

I put small but completed programs under "main" and all of them should have the main entry for execution.

I put code snippet under "test" so they serve as unit tests for certain knowledge points. Copious comments are encouraged.

An important side note:
=======================
annotation and java style configuration are now the mainstream of Spring programming.

XML based configuration is discouraged to a point that you won't find them in the "main" programs.

That they are there under "test" simply serve the purpose of awareness and help gain a certain familiarity of legacy code.


