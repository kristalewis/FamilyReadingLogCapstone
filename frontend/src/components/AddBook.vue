<template>


  <!--DEMO BOOK LIST -->
  <!-- 9780916291457 -->
  <!-- 978-0-394-80016-5 --> 
  <!-- 0960057242 -->
  <!-- 1580087930 -->


  <div class="container">
      <!-- <h3>Add a Book</h3> -->
      <form v-on:submit.prevent="getBookFromApi">
        <div class="form-group">
            <p>Find your book:</p>
            <b-alert v-if="showError" show variant="danger">Sorry, couldn't find that book.</b-alert>
            <label>ISBN Number<b-form-input type="text" v-model="userInputIsbn"/></label>
            <b-button type="submit" variant="success">Find Book</b-button>
        </div>
      </form>
      <form v-on:submit.prevent="addBook">
          <p>Or enter details manually:</p>
          <div class="form-group">
            <label>Title<b-form-input type="text" v-model="book.title" required/></label>
          </div>
          <div class="form-group">
            <label>Author<b-form-input type="text" v-model="book.author" required/></label>
          </div>
          <div class="form-group">
            <label>ISBN Number<b-form-input type="text" v-model="book.isbn" required/></label>
          </div>
          <!-- <div class="form-group">
            <label>Image<b-form-input type="text" v-model="book.image" required/></label>
          </div> -->
          <!-- <div class="form-group">
            <label>Description<b-form-textarea v-model="book.description"/></label>
          </div> -->
          <b-button type="submit" variant="success">Add Book</b-button>
      </form>
  </div>
</template>

<script>
import auth from '@/auth';
export default {
    props: {
      currentUser: Object
    },
    data() {
        return {
            book: {
                title: '',
                author: '',
                isbn: '',
                image: 'none',
                description: '',
                familyId: this.currentUser.familyId
            },
            userInputIsbn: '',
            showError: false           
        }
    },
    methods: {
        addBook() {
           fetch(`${process.env.VUE_APP_REMOTE_API}/api/books/`, {
                method: 'POST',
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + auth.getToken()
                    },
                body: JSON.stringify(this.book),
            })
            .then(() => this.$emit('add-book'));
        },
        getBookFromApi() {
            fetch(`https://openlibrary.org/api/books?bibkeys=ISBN:${this.userInputIsbn}&jscmd=data&format=json`, {
            method: 'GET'
            })
            .then((response) => {
              return response.json();
            })
            .then((data) => {
              this.showError = false;
              try {
                this.book.title = data[`ISBN:${this.userInputIsbn}`].title;
                this.book.author = data[`ISBN:${this.userInputIsbn}`].by_statement;
                this.book.isbn = this.userInputIsbn.replace('-','');
                this.book.image = data[`ISBN:${this.userInputIsbn}`].cover.large;
                this.book.description = data[`ISBN:${this.userInputIsbn}`].notes;
                this.addBook(); 
              } catch (err) {
                this.showError = true;
              }
            });
        }
    }
}
</script>

<style scoped>
    label {
        width: 100%;
    }
    textarea {
        height: 100px;
    }
    div.form-group{
      margin: 0;
    }
    div.container {
      margin-top: 1%;
      border: 1px solid grey;
      border-radius: 10px;
      width: 40%;
      margin-left: 0;
    }
    form {
      padding-top: 3%;
      padding-bottom: 3%;
    }
    p {
      color: #17a2b8;
      font-weight: bold;
    }
</style>