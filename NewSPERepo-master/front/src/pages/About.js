// import userContext from "../context/userContext";
// import Base from "../components/Base";

// const About = () => {
//   return (
//     <userContext.Consumer>
//       {(object) => (
//         <Base>
//           <h1>this is about page</h1>
//           <p>we are building blog website</p>
//           {console.log(object)}
//           <h1>Welcome user: {object.user.login && object.user.data.name}</h1>
//         </Base>
//       )}
//     </userContext.Consumer>
//   );
// };

// export default About;

import userContext from "../context/userContext";
import Base from "../components/Base";
import { Card, CardContent, Typography, Container, Box } from '@mui/material';

const About = () => {
  return (
    <userContext.Consumer>
      {(object) => (
        <Base>
          <Container style={{ marginTop: '20px' }}>
            <Typography variant="h6" component="h4" style={{ textAlign: 'center', marginBottom: '20px', fontFamily: 'Roboto, sans-serif' }}>
              Welcome user {object.user.login && object.user.data.name}
            </Typography>
            <Card style={{ maxWidth: '100%', padding: '20px', boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)' }}>
              <CardContent>
                <Typography variant="h4" component="h2" gutterBottom style={{ textAlign: 'center', fontFamily: 'Merriweather, serif' }}>
                  About Our Blog App
                </Typography>
                <Typography variant="body1" component="p" gutterBottom style={{ fontFamily: 'Open Sans, sans-serif', lineHeight: '1.6' }}>
                  Welcome to our blog app! This platform is designed to provide users with a seamless
                  blogging experience. Our app offers a variety of features to ensure that users can
                  easily create, share, and engage with content.
                </Typography>
                <Typography variant="h6" component="h3" gutterBottom style={{ marginTop: '20px', fontFamily: 'Merriweather, serif' }}>
                  Key Features:
                </Typography>
                <Box component="ul" style={{ paddingLeft: '20px', marginBottom: '20px' }}>
                  <Box component="li" style={{ marginBottom: '10px' }}>
                    <Typography variant="body1" component="span" style={{ fontFamily: 'Open Sans, sans-serif' }}>
                      Create and share blog posts effortlessly using our rich text editor.
                    </Typography>
                    <Typography variant="body2" component="p" style={{ fontFamily: 'Open Sans, sans-serif', lineHeight: '1.6' }}>
                      Our intuitive editor allows you to easily format text, insert images, and embed videos,
                      making your content visually appealing and engaging.
                    </Typography>
                  </Box>
                  <Box component="li" style={{ marginBottom: '10px' }}>
                    <Typography variant="body1" component="span" style={{ fontFamily: 'Open Sans, sans-serif' }}>
                      Engage with other users through comments, likes, and shares.
                    </Typography>
                    <Typography variant="body2" component="p" style={{ fontFamily: 'Open Sans, sans-serif', lineHeight: '1.6' }}>
                      Foster a community around your blog by interacting with readers and other bloggers. 
                      Respond to comments, like posts, and share content to broaden your reach.
                    </Typography>
                  </Box>
                  <Box component="li" style={{ marginBottom: '10px' }}>
                    <Typography variant="body1" component="span" style={{ fontFamily: 'Open Sans, sans-serif' }}>
                      Discover new content tailored to your interests using our advanced recommendation system.
                    </Typography>
                    <Typography variant="body2" component="p" style={{ fontFamily: 'Open Sans, sans-serif', lineHeight: '1.6' }}>
                      Our platform uses intelligent algorithms to recommend posts and bloggers that match your
                      interests, ensuring you always have fresh and relevant content to read.
                    </Typography>
                  </Box>
                </Box>
                <Typography variant="body1" component="p" gutterBottom style={{ fontFamily: 'Open Sans, sans-serif', lineHeight: '1.6', marginBottom: '20px' }}>
                  Our mission is to create a vibrant and supportive community where everyone can share their
                  thoughts, ideas, and experiences. Whether you are a seasoned blogger or just starting out,
                  our platform provides the tools you need to succeed.
                </Typography>
                <Typography variant="body1" component="p" gutterBottom style={{ fontFamily: 'Open Sans, sans-serif', lineHeight: '1.6', marginBottom: '20px' }}>
                  We are constantly updating and improving our features to ensure the best possible experience
                  for our users. Thank you for being a part of our community!
                </Typography>
              </CardContent>
            </Card>
          </Container>
        </Base>
      )}
    </userContext.Consumer>
  );
};

export default About;
