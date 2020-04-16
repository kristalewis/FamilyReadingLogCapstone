<template>
  <div class="container">
    <h3>Our Family Bookshelf</h3>
    <div id="top-image">
      <img id="bookshelfImage" src='@/assets/images/bookshelfSmall.jpg'/>
    </div>
    <button class="btn btn-info" v-on:click.prevent="toggleBookForm">{{bookFormToggleText}}</button>
      <div id="form" v-if="showBookForm == true">
        <add-book v-bind:currentUser="currentUser" v-on:add-book="toggleAndLoadBooks"/>
      </div>
      <div class="bookCards">
        <div class="book" v-for="book in books" v-bind:key="book.bookId">
            <div class="bookCover">
              <img v-if="!book.image.includes('jpg')" id="bookImg" src="@/assets/images/bookicon.jpg"/>
              <img v-else id="bookImg" :src="book.image"/>
            </div>
            <div id="title">
              <span class="bold"> {{book.title}} </span>
            </div>
            <div id="author">
              <span> Author: {{book.author}} </span>
            </div>
            <div id="isbn">
              <p> ISBN: {{book.isbn}} </p>
            </div>
            <!-- <div id="description"> -->
              <!-- If we have time, we could use Vue's toggle, collapse feature to toggle the description. It's an unknown size that can affect the size of the book "card" on this page  -->
                <!-- <p> Description: {{book.description}} </p>
            </div> -->
        </div>
      </div>
  </div>
</template>

<script>
import auth from '@/auth';
import AddBook from '@/components/AddBook.vue';

export default {
  props: {
    currentUser: Object
  },
  data() {
    return {
       books: [],
       showBookForm: false,
       bookFormToggleText: 'Add Book'
    }
  },
  components: {
    AddBook
  },
  methods: {
    getAllBooks() {
       fetch(`${process.env.VUE_APP_REMOTE_API}/api/books/${this.currentUser.familyId}`, {
        method: 'GET',
          headers: {
          Accept: 'application/json',
          Authorization: 'Bearer ' + auth.getToken()
        }
        })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.books = data;
        });
    },
    toggleBookForm() {
      this.showBookForm = !this.showBookForm;
      this.showBookForm ? this.bookFormToggleText = 'Hide Form': this.bookFormToggleText = 'Add Book';
    },
    toggleAndLoadBooks() {
      this.toggleBookForm();
      this.getAllBooks();
    }
  },
  created() {
    this.getAllBooks()
  }
}
</script>

<style scoped>
  .bold {
    font-weight: bold;
  }

  .bookCards{
    display: flex;
    flex: row;
    flex-wrap: wrap;
    width: 91vw;
  }

  div.book {
    border: 2px solid lightgrey;
    border-radius: 5px;
    margin: 1%;
    padding: 10px;
    width: 20%;
    max-height: 50%;
  }
  
  #bookshelfImage {
    width: 91vw;
    height: auto;
       /* make responsive */
  }
  div#top-image {
    max-height: 200px; 
    /* make responsive */
    overflow: hidden;
    border-radius: 15px;
    margin-bottom: 2%;
  }

  #bookImg{
    width: 50%;
    background-color: red;
  }

  
</style>