<template>
  <div class="container">
      <h3>Activity Log</h3>
      <div id="top-image">
        <img id="bookshelfImage" src='@/assets/images/rowofbooks.jpg'/>
      </div>
      <button class="btn btn-info" v-on:click.prevent="toggleLogForm">{{logFormToggleText}}</button>
      <div id="form" v-if="showLogForm == true">
        <div v-if="showLogForm">
        <div id="addBookOption">
          <p>Not Seeing Your Book?</p>
          <router-link v-bind:to="{name : 'bookshelf'}" class="btn btn-info" tag="button">Add Book</router-link>
        </div>
          <add-reading-activity v-bind:currentUser="currentUser" v-on:add-log="toggleAndLoadLogs"/>
        </div>
      </div>
      <h4 id="activityTitle">Activity Summary</h4>
      <div id="activitySummary">
        <div id="activity-display-container">
          <div class="activityPieces">
            <p><span class="grey">Number of Books Completed :</span> {{completedBooks.length}}</p>
            <p><span class="grey">Reading Time :</span> {{totalReadingTime}} minutes</p>
          </div>
          <div class="activityPieces">
            <div v-if="currentBooks.length > 0" class="currentBooks">
              <p><span class="grey">Currently Reading :</span> </p><ul id="readingList"><li class="booksInReadingList" 
              v-for="book in currentBooks" v-bind:key="book.bookId">{{book.title}} </li></ul>
            </div>
            <div v-else class="currentBooks">
              <p>You are not reading any books right now</p>
            </div>
          </div>
        </div>
      </div>
      <h4>Recent Activity</h4>
      <div id="log-display-container">
        <div class="log" v-for="log in logs" v-bind:key="log.logId">
          <p><span class="grey">Book :</span> {{log.title}} </p>
          <p><span class="grey">Duration :</span> {{log.minutesSpent}} minutes</p>
          <p><span class="grey">Category :</span> {{log.readingFormat}} </p>
          <p><span class="grey">Notes :</span> {{log.bookNotes}} </p>
        </div>
        <div class="log" id="blank-log" v-if="logs.length % 2 == 1">
        </div>
      </div>
  </div>
</template>

<script>
import auth from '@/auth';
import AddReadingActivity from '@/components/AddReadingActivity.vue';
export default {
    props: {
      currentUser: Object
    },
  data() {
    return {
      logs: [],
      showLogForm: false,
      logFormToggleText: 'Add Activity',
      currentBooks: [],
      completedBooks: []
    }
  },
  components: {
    AddReadingActivity
  },
  methods: {
    getAllLogs() {
        fetch(`${process.env.VUE_APP_REMOTE_API}/api/activity/user/${this.currentUser.id}`, {
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
            this.logs = data;
          });
    },
    toggleLogForm() {
      this.showLogForm = !this.showLogForm;
      this.showLogForm ? this.logFormToggleText = 'Hide Form' : this.logFormToggleText = 'Add Activity';
    },
    toggleAndLoadLogs() {
      this.toggleLogForm();
      this.getAllLogs()
      this.getCurrentBooks();
      this.getCompletedBooks();
    },
    getCurrentBooks() {
       fetch(`${process.env.VUE_APP_REMOTE_API}/api/books/personalbookshelf/${this.currentUser.id}`, {
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
              this.currentBooks = data;
            });
    },
    getCompletedBooks() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/books/completed/${this.currentUser.id}`, {
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
              this.completedBooks = data;
            });
    }
  },
  created() {
    this.getAllLogs()
    this.getCurrentBooks();
    this.getCompletedBooks();
  },
  computed: {
    totalReadingTime: function() {
      return this.logs.reduce((total, current) => 
        total + current.minutesSpent, 0
      )
    }
  } 
}
</script>

<style scoped>
p {
  margin: 0;
}
#log-display-container {
  display: flex;
  flex-wrap: wrap;
}

#activity-display-container {
  display: flex;
  justify-content: space-around;
  width: 100%;
}

div.log {
    border: 2px solid lightgrey;
    border-radius: 10px;
    margin-top: 2%;
    padding: 5px;
    width: 45%;
    margin-left: auto;
    margin-right: auto;
}

#activityTitle{
  margin-top: 2%;
}

.activityPieces {
    border: 2px solid lightgrey;
    border-radius: 10px;
    padding: 5px;
    width: 45%;
    margin-left: auto;
    margin-right: auto;
} 
span.grey {
  color: grey;
}
#form {
  margin-top: 2%;
  border: 1px solid grey;
  border-radius: 10px;
  padding: 10px;
  padding-bottom: 0;
  margin-bottom: 2%;
  width: 60%;
}
button.formSwitch {
  position: absolute;
  right: 0;
  margin-right: 42%;
}
div#activitySummary {
  display: flex;
  flex-wrap: wrap;
  margin-top: 3%;
  margin-bottom: 3%;
}
div.container {
  margin-bottom: 3%;
}
div#addBookOption {
  position: absolute;
  right: 0;
  margin-right: 42%;
}
div#blank-log {
  border: none;
}

div#top-image {
    max-height: 200px; 
    /* make responsive */
    overflow: hidden;
    border-radius: 15px;
    margin-bottom: 2%;
  }

  #bookshelfImage {
    width: 91vw;
    height: auto;
       /* make responsive */
  }

  /* #readingList{
    list-style: none;
  } */

  /* .booksInReadingList{
  display: inline;
} */
</style>