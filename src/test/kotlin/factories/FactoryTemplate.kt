package factories

// create a a factory template
// ImageFactory, UserFactory and PostFactory will inherit from this
// the method create should persiste to the database
// the method make should only return an object (without persisting it)
// there should be 4 methods: makeOne, makeMany, createOne, createMany
// the methods will create the data using the faker library

// a User can have many posts and a Post can have many images

// example:
// val postDto = PostFactory(postRepository).makeOne(1)
// PostFactory(postRepository).createMany(3, 1)

class FactoryTemplate {




}