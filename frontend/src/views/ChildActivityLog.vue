<template>
<div class="container">
  <h1>Child Activity</h1>
  <div id="top-image">
        <img id="bookshelfImage" src='@/assets/images/rowofbooks.jpg'/>
  </div>
    <div id="buttonAndForm">
      <button class="btn btn-info" v-on:click.prevent="toggleForm">{{logFormToggleText}}</button>
      <div id="form" v-if="showForm == true">
        <div id="addBookOption">
            <p>Not Seeing Your Book?</p>
            <router-link v-bind:to="{name : 'bookshelf'}" class="btn btn-info" tag="button">Add Book</router-link>
            <p>No Readers Coming Up?</p>
            <router-link v-bind:to="{name : 'accounts'}" class="btn btn-info" tag="button">Add Child To Family</router-link>
        </div>
        <add-child-activity v-bind:currentUser="currentUser" v-on:add-log="toggleAndLoadLogs"/>
      </div>
    </div>
  <h4>Filter</h4>
  <div id="filtersAndSummary">
    <div class="activityPieces">
      
      <button class="btn btn-info filterButton" v-on:click="changeFilter(0)">All</button>
      <button class="btn btn-info filterButton" v-for="child in children" v-bind:key="child.id" 
      v-bind:value="child.id" v-on:click="changeFilter(child.id)">
          {{child.username}}
      </button>
    </div>
    <div class="activityPieces" id="summaryOnly">
        <h4>Activity Summary</h4>
        <p><span class="grey">Number of Books Completed : </span>{{completedBooks.length}}</p>
        <p><span class="grey">Reading Time : </span>{{totalReadingTime}} minutes</p>
        <div v-if="currentBooks.length > 0" class="currentBooks">
          <p><span class="grey">Currently Reading : </span></p><ul id="readingList"><li v-for="book in currentBooks" v-bind:key="book.bookId">{{book.title}}</li></ul>
        </div>
        <div v-else class="currentBooks">
          <p class="bold">Not reading any books right now</p>
        </div>
    </div>
  </div>

  <h4>Recent Activity</h4>
  <div id="log-display-container">
    <div class="log" v-for="log in logsToShow" v-bind:key="log.logId">
      <p><span class="grey">Reader :</span> {{log.username}} </p>
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
import AddChildActivity from '@/components/AddChildActivity.vue';
export default {
    props: {
      currentUser: Object
    },
    data() {
        return {
            logs: [],
            showForm: false,
            logFormToggleText: 'Add Child Activity',
            completedBooks: [],
            currentBooks: [],
            children: [],
            currentFilter: 0
        }
    },
    components: {
       AddChildActivity
    },
    methods: {
        getAllChildLogs() {
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/activity/children/${this.currentUser.familyId}`, {
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
            })
        },
        changeFilter(childId) {
            this.currentFilter = childId;
            this.showForm = false;
            this.logFormToggleText = 'Add Child Activity';
            if (childId === 0) {
              this.getAllBooks();
              this.getCompletedBooks();
            } else {
              this.getCurrentBooksForPerson();
              this.getCompletedBooksForPerson();
            }
        },
        getAllChildren() {
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/users/child/${this.currentUser.familyId}`, {
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
                    this.children = data;
                });
        },
        toggleForm() {
            this.showForm = !this.showForm;
            this.showForm ? this.logFormToggleText = 'Hide Form' : this.logFormToggleText = 'Add Child Activity';
        },
        toggleAndLoadLogs() {
          this.getAllChildLogs();
          this.toggleForm();
          this.getCompletedBooks();
        },
        getCompletedBooks() {
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/books/completed/child/${this.currentUser.familyId}`, {
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
        },
        getCompletedBooksForPerson() {
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/books/completed/${this.currentFilter}`, {
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
        },
        getAllBooks() {
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/books/childbookshelf/${this.currentUser.familyId}`, {
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
        getCurrentBooksForPerson() {
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/books/personalbookshelf/${this.currentFilter}`, {
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
        }
    },
    created() {
        this.getAllChildLogs();
        this.getAllBooks();
        this.getCompletedBooks();
        this.getAllChildren();
    },
    computed: {
    totalReadingTime: function() {
      return this.logsToShow.reduce((total, current) => 
        total + current.minutesSpent, 0
      )
    },
    logsToShow: function() {
        if (this.currentFilter === 0) {
            return this.logs;
        } else {
            return this.logs.filter(log => log.userId === this.currentFilter)
        }
    }
  }
}
</script>

<style scoped>
p {
 margin: 0;
}

.bold{
  font-weight: bold;
}

span.grey {
  color: grey;
}

#buttonAndForm{
  margin-top: 2%;
  margin-bottom: 2%;
}

div#filtersAndSummary {
  display: flex;
  justify-content: space-around;
  width: 100%;
}

.activityPieces {
    /* border: 2px solid lightgrey; */
    border-radius: 10px;
    padding: 5px;
    width: 45%;
    margin-left: auto;
    margin-right: auto;
} 

#summaryOnly{
    border: 2px solid lightgrey;
    border-radius: 10px;
}

#log-display-container {
  display: flex;
  flex-wrap: wrap;
}
#form {
  border: 1px solid grey;
  border-radius: 10px;
  padding: 10px;
  padding-bottom: 0;
  width: 60%;
  margin: 0;
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
.filterButton {
  margin-top: 5%;
  margin-right: 5%;
  margin-bottom: 5%;
}
div.container {
  margin-bottom: 3%;
}
div#activitySummary {
  margin-left: 10%;
}
div#addBookOption {
  position: absolute;
  right: 0;
  margin-right: 42%;
}
div#addBookOption p {
  margin-top: 8%;
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
</style>