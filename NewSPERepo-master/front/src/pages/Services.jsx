// import userContext from "../context/userContext"
// import Base from "../components/Base"

// const Services = () => {
//     return (
//         <userContext.Consumer>
//             {
//                 (object) => (

//                     <Base>
//                         <h1>
//                             This is services page
//                         </h1>
//                         <h1>Welcome {object.user.login && object.user.data.name}</h1>
//                     </Base>
//                 )
//             }
//         </userContext.Consumer>
//     )
// }

// export default Services

import userContext from "../context/userContext";
import Base from "../components/Base";
import { Card, CardContent, Typography, Container, Box } from '@mui/material';

const Services = () => {
  return (
    <userContext.Consumer>
      {(object) => (
        <Base>
          <Container style={{ marginTop: '20px' }}>
            <Typography variant="h6" component="h4" style={{ textAlign: 'center', marginBottom: '20px', fontFamily: 'Roboto, sans-serif' }}>
              Welcome {object.user.login && object.user.data.name}
            </Typography>
            <Card style={{ maxWidth: '100%', padding: '20px', boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)' }}>
              <CardContent>
                <Typography variant="h4" component="h2" gutterBottom style={{ textAlign: 'center', fontFamily: 'Merriweather, serif' }}>
                  Our Services
                </Typography>
                <Typography variant="body1" component="p" gutterBottom style={{ fontFamily: 'Open Sans, sans-serif', lineHeight: '1.6' }}>
                  Our blog platform offers a suite of services designed to enhance your blogging journey. With a range of features, we aim to make content creation and engagement as effortless as possible.
                </Typography>
                <Typography variant="h6" component="h3" gutterBottom style={{ marginTop: '20px', fontFamily: 'Merriweather, serif' }}>
                  Key Features:
                </Typography>
                <Box component="ul" style={{ paddingLeft: '20px', marginBottom: '20px' }}>
                  <Box component="li" style={{ marginBottom: '10px' }}>
                    <Typography variant="body1" component="span" style={{ fontFamily: 'Open Sans, sans-serif' }}>
                      Seamlessly create and share blog posts using our rich text editor.
                    </Typography>
                    <Typography variant="body2" component="p" style={{ fontFamily: 'Open Sans, sans-serif', lineHeight: '1.6' }}>
                      Our user-friendly editor allows you to easily format your text, add images, and embed videos, making your content more engaging and visually appealing.
                    </Typography>
                  </Box>
                  <Box component="li" style={{ marginBottom: '10px' }}>
                    <Typography variant="body1" component="span" style={{ fontFamily: 'Open Sans, sans-serif' }}>
                      Interact with the community through comments, likes, and shares.
                    </Typography>
                    <Typography variant="body2" component="p" style={{ fontFamily: 'Open Sans, sans-serif', lineHeight: '1.6' }}>
                      Build a community around your blog by engaging with readers and other bloggers. Respond to comments, like posts, and share content to increase your reach and influence.
                    </Typography>
                  </Box>
                  <Box component="li" style={{ marginBottom: '10px' }}>
                    <Typography variant="body1" component="span" style={{ fontFamily: 'Open Sans, sans-serif' }}>
                      Discover content that matches your interests with our recommendation system.
                    </Typography>
                    <Typography variant="body2" component="p" style={{ fontFamily: 'Open Sans, sans-serif', lineHeight: '1.6' }}>
                      Our platform leverages advanced algorithms to suggest posts and bloggers that align with your interests, ensuring you always have relevant and exciting content to explore.
                    </Typography>
                  </Box>
                </Box>
                <Typography variant="body1" component="p" gutterBottom style={{ fontFamily: 'Open Sans, sans-serif', lineHeight: '1.6', marginBottom: '20px' }}>
                  Our goal is to foster a supportive and dynamic community where users can freely share their ideas and experiences. Whether you're a veteran blogger or a novice, our platform provides all the necessary tools for success.
                </Typography>
                <Typography variant="body1" component="p" gutterBottom style={{ fontFamily: 'Open Sans, sans-serif', lineHeight: '1.6', marginBottom: '20px' }}>
                  We are dedicated to continuously enhancing our features to provide you with the best possible experience. Thank you for being an integral part of our community!
                </Typography>
              </CardContent>
            </Card>
          </Container>
        </Base>
      )}
    </userContext.Consumer>
  );
};

export default Services;
