<template>
  <div id="list-rendering">
    <ol>
      <li v-for="(todo, index) in todos" :key="todo.id">
        {{parentMsg}} - {{index}}: => {{ todo.text }}
      </li>
    </ol>
    <p></p>
    <ul>
      <li v-for="value in myObject" :key="value">
        {{value}}
      </li>
    </ul>
    <p>Displaying Filtered/Sorted Results</p>
    <ul>
      <li v-for="n in even_numbers" :key="n">{{n}}</li>
    </ul>
    <span v-for="n in 10" :key="n">{{n}}</span>


  </div>
</template>

<script>
export default {
  name: "ListRendering",
  data(){
    return {
      parentMsg : 'parent',
      todos:[
        {id:1, text:"this is one", isCompleted:true},
        {id:2, text:"this is two", isCompleted:false},
        {id:3, text:"this is three", isCompleted:true},
        {id:4, text:"this is four", isCompleted:true},
        {id:5, text:"this is five", isCompleted:false}
      ],
      myObject : {
        title : "Harry Potter",
        author: "I forgot",
        publishAt: "2000"
      },
      numbers : [1,2,3,4,5,6,7,8,9,10]
    }
  },
  computed :{
    even_numbers(){
      return this.numbers.filter(n => n%2===0)
    }
  }
}
</script>

<style scoped>

</style>