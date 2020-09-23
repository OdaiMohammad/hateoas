[![license](https://img.shields.io/github/license/OdaiMohammad/hateoas)](https://img.shields.io/github/license/OdaiMohammad/hateoas)
[![test](https://img.shields.io/github/workflow/status/OdaiMohammad/hateoas/Test%20workflow?label=test)](https://img.shields.io/github/workflow/status/OdaiMohammad/hateoas/Test%20workflow?label=test)
[![open issues](https://img.shields.io/github/issues-raw/OdaiMohammad/hateoas)](https://img.shields.io/github/issues-raw/OdaiMohammad/hateoas)
# Spring HATEOAS abstraction
## About Spring HATEOAS
"Spring HATEOAS allows for creating self-describing APIs wherein resources returned from an 
API contain links to related resources. 
This enables clients to navigate an API with minimal understanding of the API’s URLs. 
Instead, it understands relationships between the resources served by the API 
and uses its understanding of those relationships to discover the API’s URLs 
as it traverses those relationships.
The Spring HATEOAS project brings hyperlink support to Spring. It offers a set of
classes and resource assemblers that can be used to add links to resources before
returning them from a Spring MVC controller."

—Craig Walls - [Spring in Action](https://www.manning.com/books/spring-in-action-fifth-edition) 

## Motivation
While the Spring HATEOAS project allows for easy support of HATEOAS in Spring, 
I still find working with it tedious. Writing boilerplate code for creating resources and resource assemblers
gets old quickly.

### Example
Let's look at an example from [Spring in Action](https://www.manning.com/books/spring-in-action-fifth-edition) by Craig Walls
:

Say we have the following "Taco" object that we would like to return a list of to the client.

```java
@Data
public class Taco {
    private String name;
    private List<String> ingredients;
}
```
Spring HATEOAS requires us to write a resource for a Taco. That would look something like this:

```java
public class TacoResource extends ResourceSupport {

   @Getter
   private final String name;
   
   @Getter
   private final Date createdAt;
   
   @Getter
   private final List<Ingredient> ingredients;
   
   public TacoResource(Taco taco) { 
       this.name = taco.getName();
       this.createdAt = taco.getCreatedAt();
       this.ingredients = taco.getIngredients(); 
   }
   
}
   ```
   
Then we'd have to write a resource assembler:
```java
public class TacoResourceAssembler
       extends ResourceAssemblerSupport<Taco, TacoResource> {
    
    public TacoResourceAssembler() {
        super(DesignTacoController.class, TacoResource.class);
    }

    @Override
    protected TacoResource instantiateResource(Taco taco) {
        return new TacoResource(taco);
    }
    
    @Override
    public TacoResource toResource(Taco taco) {
        return createResourceWithId(taco.getId(), taco);
    }

}
```

Finally, we can use our Taco resource and resource assembler to return a self-describing response to the client:
```java
@GetMapping("/recent")
public Resources<TacoResource> recentTacos() {
    PageRequest page = PageRequest.of(
            0, 12, Sort.by("createdAt").descending());
    
    List<Taco> tacos = tacoRepo.findAll(page).getContent();
    
    List<TacoResource> tacoResources = 
        new TacoResourceAssembler().toResources(tacos);
    
    Resources<TacoResource> recentResources =
        new Resources<TacoResource>(tacoResources);
    
    recentResources.add(
            linkTo(methodOn(DesignTacoController.class).recentTacos())
            .withRel("recents"));
    
    return recentResources;
}
```

You can probably see from this example, that this process becomes more tedious as we add more and more 
domain objects.

## Goal
Implement an abstraction of Spring HATEOAS to streamline the creation of resources and resource assemblers
using Java annotations that will create these classes for us.

## About the sample application
The sample application included in the repository exposes an extremely simple RESTful API for a project management app.

The app allows for viewing or adding projects along with their associated tasks. It uses an H2 embedded database so it can portable. 

There's really not much more to say about the sample application.